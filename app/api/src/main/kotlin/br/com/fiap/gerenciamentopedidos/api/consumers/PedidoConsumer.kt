package br.com.fiap.gerenciamentopedidos.api.consumers

import br.com.fiap.gerenciamentopedidos.api.adapters.interfaces.PedidoAdapter
import br.com.fiap.gerenciamentopedidos.api.requests.AlterarStatusPedidoRequest
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class PedidoConsumer(private val pedidoAdapter: PedidoAdapter) {
    @RabbitListener(
        queues = [
            "\${queue.pedido-status.name}",
            "\${queue.pedido-finalizado.name}"
        ]
    )
    fun alterarStatusPedido(alterarStatusPedidoRequest: AlterarStatusPedidoRequest) =
        pedidoAdapter.alterarStatusPedido(alterarStatusPedidoRequest)
}
