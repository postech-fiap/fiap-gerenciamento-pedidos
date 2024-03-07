package br.com.fiap.gerenciamentopedidos.domain.usecases.pedido

import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.gateways.NotificacaoGateway
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.AlterarStatusPedidoUseCase
import org.springframework.transaction.annotation.Transactional

@Transactional
class AlterarStatusPedidoUseCaseImpl(
    private val pedidoRepository: PedidoRepository,
    private val notificacaoGateway: NotificacaoGateway
) : AlterarStatusPedidoUseCase {
    override fun executar(id: Long, status: PedidoStatus?, pagamentoId: String?, pagamentoStatus: PagamentoStatus?) {
        val pedido = pedidoRepository
            .buscarPedidoPorId(id)
            .orElseThrow { RecursoNaoEncontradoException("Pedido não encontrado") }

        status?.let { pedido.alterarStatus(it) }
        pagamentoStatus?.let { pedido.alterarPagamentoStatus(it) }
        pagamentoId?.let { pedido.pagamentoId = it }

        pedidoRepository.update(pedido)
        notificacaoGateway.notificarPedidoAlterado(pedido)
    }
}
