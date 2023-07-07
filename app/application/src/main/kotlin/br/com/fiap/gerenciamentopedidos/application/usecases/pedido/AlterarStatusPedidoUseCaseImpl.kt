package br.com.fiap.gerenciamentopedidos.application.usecases.pedido

import br.com.fiap.gerenciamentopedidos.application.interfaces.pedido.AlterarStatusPedidoUseCase
import br.com.fiap.gerenciamentopedidos.application.requests.AlterarStatusPedidoRequest
import br.com.fiap.gerenciamentopedidos.domain.exceptions.BusinessException
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import org.springframework.transaction.annotation.Transactional

@Transactional
class AlterarStatusPedidoUseCaseImpl(
    private val pedidoRepository: PedidoRepository
) : AlterarStatusPedidoUseCase {
    override fun executar(alterarStatusPedidoRequest: AlterarStatusPedidoRequest) {
        val pedido = pedidoRepository.buscarPedidoPorId(alterarStatusPedidoRequest.pedidoId)

        try {
            return when (pedido.status) {
                alterarStatusPedidoRequest.status -> throw BusinessException("O status do pedido ja está igual à ${alterarStatusPedidoRequest.status}")
                else -> pedidoRepository.alterarStatusPedido(pedido.copy(status = alterarStatusPedidoRequest.status))
            }
        } catch (ex: Exception) {

        }
    }
}