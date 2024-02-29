package br.com.fiap.gerenciamentopedidos.domain.usecases.pedido

import br.com.fiap.gerenciamentopedidos.domain.dtos.ItemPedidoDto
import br.com.fiap.gerenciamentopedidos.domain.dtos.NotificacaoDto
import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.gateways.NotificacaoGateway
import br.com.fiap.gerenciamentopedidos.domain.interfaces.gateways.ProducaoGateway
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.AlterarStatusPedidoUseCase
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import org.springframework.transaction.annotation.Transactional

@Transactional
class AlterarStatusPedidoUseCaseImpl(
    private val pedidoRepository: PedidoRepository,
    private val producaoGateway: ProducaoGateway,
    private val notificacaoGateway: NotificacaoGateway
) : AlterarStatusPedidoUseCase {
    override fun executar(id: Long, status: PedidoStatus?, pagamentoStatus: PagamentoStatus?) {
        val pedido = pedidoRepository
            .buscarPedidoPorId(id)
            .orElseThrow { RecursoNaoEncontradoException("Pedido não encontrado") }

        status.let { pedido.alterarStatus(it!!) }
        pagamentoStatus.let { pedido.alterarPagamentoStatus(it!!) }

        pedidoRepository.update(pedido)

        if (pedido.isAprovado())
            producaoGateway.enviar(criarPedidoDto(pedido))

        notificar(pedido)
    }

    private fun criarPedidoDto(pedido: Pedido) = PedidoDto(
        pedido.id,
        pedido.numero,
        pedido.dataHora,
        pedido.items.map { ItemPedidoDto(it.produto!!.nome, it.quantidade, it.comentario) }
    )

    private fun notificar(pedido: Pedido) {
        if (!pedido.clienteId.isNullOrEmpty()) {
            notificacaoGateway.enviar(
                NotificacaoDto(
                    idPedido = pedido.id!!,
                    idCliente = pedido.clienteId!!,
                    numeroPedido = pedido.numero!!,
                    status = pedido.status,
                )
            )
        }
    }
}
