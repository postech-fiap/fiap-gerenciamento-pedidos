package br.com.fiap.gerenciamentopedidos.application.usecases.pedido

import br.com.fiap.gerenciamentopedidos.application.interfaces.pedido.AlterarStatusPedidoUseCase
import br.com.fiap.gerenciamentopedidos.application.requests.AlterarStatusPedidoRequest
import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoDto
import br.com.fiap.gerenciamentopedidos.domain.exceptions.BusinessException
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository

class AlterarStatusPedidoUseCaseImpl(
    private val pedidoPort: PedidoRepository
) : AlterarStatusPedidoUseCase {
    override fun executar(alterarStatusPedidoRequest: AlterarStatusPedidoRequest): PedidoDto {
        val pedido = pedidoPort.buscarPedidoPorId(alterarStatusPedidoRequest.pedidoId)

        return when (pedido.status) {
            alterarStatusPedidoRequest.status -> throw BusinessException("O status do pedido ja está igual à ${alterarStatusPedidoRequest.status}")
            else -> pedidoPort.alterarStatusPedido(pedido.copy(status = alterarStatusPedidoRequest.status))
        }
    }
}