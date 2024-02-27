package br.com.fiap.gerenciamentopedidos.infrastructure.gateways

import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoDto
import br.com.fiap.gerenciamentopedidos.domain.interfaces.gateways.ProducaoGateway
import br.com.fiap.gerenciamentopedidos.infrastructure.exceptions.IntegracaoAPIException
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.core.RabbitTemplate

private const val ERROR_MESSAGE = "Erro de integração para enviar pedido para produção. Detalhes: %s"

class ProducaoGatewayImpl(private val queue: Queue, private val rabbitTemplate: RabbitTemplate) : ProducaoGateway {
    override fun enviar(pedido: PedidoDto) {
        try {
            rabbitTemplate.convertAndSend(this.queue.name, pedido)
        } catch (ex: Exception) {
            throw IntegracaoAPIException(String.format(ERROR_MESSAGE, ex.message))
        }
    }
}