package br.com.fiap.gerenciamentopedidos.domain.usecases.pedido

import br.com.fiap.gerenciamentopedidos.domain.dtos.ItemPagamentoDto
import br.com.fiap.gerenciamentopedidos.domain.dtos.PagamentoDto
import br.com.fiap.gerenciamentopedidos.domain.dtos.ProdutoPagamentoDto
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.gateways.PagamentoGateway
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.CadastrarPedidoUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.GerarNumeroPedidoUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.produto.ObterProdutosPorIdsUseCase
import br.com.fiap.gerenciamentopedidos.domain.models.Item
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import java.util.*

class CadastrarPedidoUseCaseImpl(
    private val pedidoRepository: PedidoRepository,
    private val gerarNumeroPedidoUseCase: GerarNumeroPedidoUseCase,
    private val obterProdutosPorIdsUseCase: ObterProdutosPorIdsUseCase,
    private val pagamentoGateway: PagamentoGateway
) : CadastrarPedidoUseCase {
    override fun executar(clienteId: Long?, itens: List<Item>): Pedido {
        val pedido = Pedido(
            numero = gerarNumeroPedidoUseCase.executar(),
            clienteId = clienteId,
            referencia = UUID.randomUUID()
        )

        val produtos = obterProdutosPorIdsUseCase.executar(itens.map { it.id!! })

        itens.map {
            val produto = produtos.firstOrNull { p -> p.id == it.id!! && p.disponivel }
                ?: throw RecursoNaoEncontradoException("Produto ${it.produto?.id!!} não encontrado ou indisponível")

            pedido.adicionarItem(produto, it.quantidade, it.comentario)
        }

        val pagamento = criarPagamento(pedido)

        pedido.addPagamento(pagamento.id!!, pagamento.status!!)

        return pedidoRepository.salvar(pedido.valid())
    }

    private fun criarPagamento(pedido: Pedido) = pagamentoGateway.criar(PagamentoDto(
        referenciaPedido = pedido.referencia.toString(),
        numeroPedido = pedido.numero,
        dataHora = pedido.dataHora,
        valorTotal = pedido.valorTotal,
        items = pedido.items.map {
            ItemPagamentoDto(
                quantidade = it.quantidade,
                valorPago = it.valorPago,
                produto = ProdutoPagamentoDto(
                    id = it.produto!!.id,
                    nome = it.produto.nome,
                    descricao = it.produto.descricao,
                    categoria = it.produto.categoria.toString(),
                    valor = it.produto.valor,
                )
            )
        }
    ))
}
