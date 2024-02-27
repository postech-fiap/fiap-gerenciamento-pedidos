package br.com.fiap.gerenciamentopedidos.infrastructure.gateways

import br.com.fiap.gerenciamentopedidos.domain.dtos.PagamentoDto
import br.com.fiap.gerenciamentopedidos.domain.interfaces.gateways.PagamentoGateway
import br.com.fiap.gerenciamentopedidos.infrastructure.exceptions.IntegracaoAPIException
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.core.RabbitTemplate

private const val ERROR_MESSAGE = "Erro de integração para gerar o pagamento. Detalhes: %s"

class PagamentoGatewayImpl(private val queue: Queue, private val rabbitTemplate: RabbitTemplate) : PagamentoGateway {
    override fun criar(pagamento: PagamentoDto) {
        try {
            rabbitTemplate.convertAndSend(this.queue.name, pagamento)
        } catch (ex: Exception) {
            throw IntegracaoAPIException(String.format(ERROR_MESSAGE, ex.message))
        }
    }
}