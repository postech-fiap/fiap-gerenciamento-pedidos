package br.com.fiap.gerenciamentopedidos.domain.usecases.pedido

import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.gateways.NotificacaoGateway
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.CadastrarPedidoUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.GerarNumeroPedidoUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.produto.ObterProdutosPorIdsUseCase
import br.com.fiap.gerenciamentopedidos.domain.models.Item
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import org.springframework.transaction.annotation.Transactional

open class CadastrarPedidoUseCaseImpl(
    private val pedidoRepository: PedidoRepository,
    private val gerarNumeroPedidoUseCase: GerarNumeroPedidoUseCase,
    private val obterProdutosPorIdsUseCase: ObterProdutosPorIdsUseCase,
    private val notificacaoGateway: NotificacaoGateway
) : CadastrarPedidoUseCase {
    @Transactional
    override fun executar(clienteId: String?, itens: List<Item>): Pedido {
        val pedido = Pedido(
            numero = gerarNumeroPedidoUseCase.executar(),
            clienteId = clienteId
        )

        val produtos = obterProdutosPorIdsUseCase.executar(itens.map { it.id!! })

        itens.map {
            val produto = produtos.firstOrNull { p -> p.id == it.id!! && p.disponivel }
                ?: throw RecursoNaoEncontradoException("Produto ${it.produto?.id!!} não encontrado ou indisponível")
            pedido.adicionarItem(produto, it.quantidade, it.comentario)
        }

        val pedidoCriado = pedidoRepository.salvar(pedido.valid())

        notificacaoGateway.notificarPedidoCriado(pedidoCriado)

        return pedidoCriado
    }
}
