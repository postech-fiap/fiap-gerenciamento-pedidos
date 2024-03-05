package br.com.fiap.gerenciamentopedidos.infrastructure.gateways

import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.core.RabbitTemplate

@ExtendWith(MockKExtension::class)
class NotificacaoGatewayImplTest {

    @MockK
    private lateinit var queuePedidoCriado: Queue

    @MockK
    private lateinit var queuePedidoRecebido: Queue

    @MockK
    private lateinit var queuePedidoAlterado: Queue

    @MockK
    private lateinit var rabbitTemplate: RabbitTemplate

    @InjectMockKs
    private lateinit var notificacaoGatewayImpl: NotificacaoGatewayImpl

    @Test
    fun `deve notificar pedido criado`() {

    }

    @Test
    fun `deve propagar erro ao notificar pedido criado`() {

    }

    @Test
    fun `deve notificar pedido aprovado`() {

    }

    @Test
    fun `deve propagar erro ao notificar pedido aprovado`() {

    }

    @Test
    fun `deve notificar pedido alterado`() {

    }

    @Test
    fun `deve propagar erro ao notificar pedido alterado`() {

    }
}