package br.com.fiap.gerenciamentopedidos.domain.usecases.pedido

import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.cliente.BuscarClientePorIdUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pagamento.EfetuarPagamentoUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.CadastrarPedidoUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.GerarNumeroPedidoUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.produto.ObterProdutosPorIdsUseCase
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import br.com.fiap.gerenciamentopedidos.domain.models.Item
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido

class CadastrarPedidoUseCaseImpl(
    private val pedidoRepository: PedidoRepository,
    private val buscarClientePorIdUseCase: BuscarClientePorIdUseCase,
    private val gerarNumeroPedidoUseCase: GerarNumeroPedidoUseCase,
    private val obterProdutosPorIdsUseCase: ObterProdutosPorIdsUseCase,
    private val efetuarPagamentoUseCase: EfetuarPagamentoUseCase
) : CadastrarPedidoUseCase {
    override fun executar(clienteId: Long?, itens: List<Item>): Pedido {
        val numeroPedido = gerarNumeroPedidoUseCase.executar()
        val produtos = obterProdutosPorIdsUseCase.executar(itens.map { it.produto?.id!! })
        val pagamento = efetuarPagamentoUseCase.executar(numeroPedido)

        val pedido = Pedido(numero = numeroPedido, pagamento = pagamento)

        clienteId?.let {
            pedido.atribuirCliente(buscarClientePorIdUseCase.executar(it))
        }

        itens.map {
            val produto = produtos.firstOrNull { p -> p.id == it.produto?.id!! && p.disponivel }
                ?: throw RecursoNaoEncontradoException("Produto ${it.produto?.id!!} não encontrado ou indisponível")

            pedido.adicionarItem(produto, it.quantidade, it.comentario)
        }

        return pedidoRepository.salvar(pedido)
    }
}
