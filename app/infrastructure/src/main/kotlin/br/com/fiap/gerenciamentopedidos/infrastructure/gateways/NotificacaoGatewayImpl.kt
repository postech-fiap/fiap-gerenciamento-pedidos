package br.com.fiap.gerenciamentopedidos.infrastructure.gateways

import br.com.fiap.gerenciamentopedidos.domain.interfaces.gateways.NotificacaoGateway
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import br.com.fiap.gerenciamentopedidos.infrastructure.exceptions.IntegracaoAPIException
import br.com.fiap.gerenciamentopedidos.infrastructure.messages.PedidoAlteradoMessage
import br.com.fiap.gerenciamentopedidos.infrastructure.messages.PedidoCriadoMessage
import br.com.fiap.gerenciamentopedidos.infrastructure.messages.PedidoRecebidoMessage
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.core.RabbitTemplate

private const val ERROR_MESSAGE_PEDIDO_APROVADO = "Erro ao notificar pedido aprovado. Detalhes: %s"
private const val ERROR_MESSAGE_PEDIDO_CRIADO = "Erro ao notificar pedido criado. Detalhes: %s"

class NotificacaoGatewayImpl(
    private val queuePedidoCriado: Queue,
    private val queuePedidoRecebido: Queue,
    private val queuePedidoAlterado: Queue,
    private val rabbitTemplate: RabbitTemplate
) : NotificacaoGateway {
    override fun notificarPedidoCriado(pedido: Pedido) {
        try {
            rabbitTemplate.convertAndSend(queuePedidoCriado.name, PedidoCriadoMessage.fromModel(pedido))
            rabbitTemplate.convertAndSend(queuePedidoAlterado.name, PedidoAlteradoMessage.fromModel(pedido))
        } catch (ex: Exception) {
            throw IntegracaoAPIException(String.format(ERROR_MESSAGE_PEDIDO_CRIADO, ex.message))
        }
    }

    override fun notificarPedidoAlterado(pedido: Pedido) {
        try {
            if (pedido.isAprovado())
                rabbitTemplate.convertAndSend(queuePedidoRecebido.name, PedidoRecebidoMessage.fromModel(pedido))
            rabbitTemplate.convertAndSend(queuePedidoAlterado.name, PedidoAlteradoMessage.fromModel(pedido))
        } catch (ex: Exception) {
            throw IntegracaoAPIException(String.format(ERROR_MESSAGE_PEDIDO_APROVADO, ex.message))
        }
    }
}