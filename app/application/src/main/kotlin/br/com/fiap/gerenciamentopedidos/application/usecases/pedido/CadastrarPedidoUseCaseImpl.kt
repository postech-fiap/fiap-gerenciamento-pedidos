package br.com.fiap.gerenciamentopedidos.application.usecases.pedido

import br.com.fiap.gerenciamentopedidos.application.interfaces.cliente.BuscarClientePorIdUseCase
import br.com.fiap.gerenciamentopedidos.application.interfaces.pagamento.EfetuarPagamentoUseCase
import br.com.fiap.gerenciamentopedidos.application.interfaces.pedido.CadastrarPedidoUseCase
import br.com.fiap.gerenciamentopedidos.application.interfaces.pedido.GerarNumeroPedidoUseCase
import br.com.fiap.gerenciamentopedidos.application.interfaces.produto.ObterProdutosPorIdsUseCase
import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarPedidoRequest
import br.com.fiap.gerenciamentopedidos.application.responses.PedidoResponse
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido

class CadastrarPedidoUseCaseImpl(
    private val pedidoRepository: PedidoRepository,
    private val buscarClientePorIdUseCase: BuscarClientePorIdUseCase,
    private val gerarNumeroPedidoUseCase: GerarNumeroPedidoUseCase,
    private val obterProdutosPorIdsUseCase: ObterProdutosPorIdsUseCase,
    private val efetuarPagamentoUseCase: EfetuarPagamentoUseCase
) : CadastrarPedidoUseCase {
    override fun executar(request: CadastrarPedidoRequest): PedidoResponse {
        val numeroPedido = gerarNumeroPedidoUseCase.executar()
        val produtos = obterProdutosPorIdsUseCase.executar(request.produtoIds)
        val pagamento = efetuarPagamentoUseCase.executar(numeroPedido)

        val pedido = Pedido(numero = numeroPedido, pagamento = pagamento)

        request.clienteId?.let {
            pedido.atribuirCliente(buscarClientePorIdUseCase.executar(it))
        }

        request.produtos?.map {
            val produto = produtos.firstOrNull { p -> p.id == it.produtoId }
                ?: throw RecursoNaoEncontradoException("Produto ${it.produtoId} não encontrado ou indisponível")

            pedido.adicionarItem(produto, it.quantidade, it.comentario)
        }

        return PedidoResponse(pedidoRepository.salvar(pedido))
    }
}
