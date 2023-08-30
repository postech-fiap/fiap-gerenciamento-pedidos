package br.com.fiap.gerenciamentopedidos.domain.usecases.pedido

import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.AlterarStatusPedidoUseCase
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.exceptions.BusinessException
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import org.springframework.transaction.annotation.Transactional

@Transactional
class AlterarStatusPedidoUseCaseImpl(private val pedidoRepository: PedidoRepository) : AlterarStatusPedidoUseCase {
    override fun executar(pedidoId: Long, status: PedidoStatus) {
        val pedidoResult = pedidoRepository.buscarPedidoPorId(pedidoId)
        return when (pedidoResult.status) {
            status -> throw BusinessException("O status do pedido ja está igual à $status")
            else -> pedidoRepository.alterarStatusPedido(status, pedidoId)
        }
    }
}
