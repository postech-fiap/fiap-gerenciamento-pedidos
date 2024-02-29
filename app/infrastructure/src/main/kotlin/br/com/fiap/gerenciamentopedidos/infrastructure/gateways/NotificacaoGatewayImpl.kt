package br.com.fiap.gerenciamentopedidos.infrastructure.gateways

import br.com.fiap.gerenciamentopedidos.domain.dtos.NotificacaoDto
import br.com.fiap.gerenciamentopedidos.domain.interfaces.gateways.NotificacaoGateway
import br.com.fiap.gerenciamentopedidos.infrastructure.exceptions.IntegracaoAPIException
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.core.RabbitTemplate

private const val ERROR_MESSAGE = "Erro ao notificar cliente. Detalhes: %s"

class NotificacaoGatewayImpl(private val queue: Queue, private val rabbitTemplate: RabbitTemplate) :
    NotificacaoGateway {
    override fun enviar(notificacao: NotificacaoDto) {
        try {
            rabbitTemplate.convertAndSend(this.queue.name, notificacao)
        } catch (ex: Exception) {
            throw IntegracaoAPIException(String.format(ERROR_MESSAGE, ex.message))
        }
    }
}