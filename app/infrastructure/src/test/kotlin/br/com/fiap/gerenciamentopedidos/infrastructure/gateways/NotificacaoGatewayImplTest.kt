package br.com.fiap.gerenciamentopedidos.infrastructure.gateways

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.models.Imagem
import br.com.fiap.gerenciamentopedidos.domain.models.Item
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import br.com.fiap.gerenciamentopedidos.domain.models.Produto
import br.com.fiap.gerenciamentopedidos.infrastructure.exceptions.IntegracaoAPIException
import br.com.fiap.gerenciamentopedidos.infrastructure.messages.PedidoAlteradoMessage
import br.com.fiap.gerenciamentopedidos.infrastructure.messages.PedidoCriadoMessage
import br.com.fiap.gerenciamentopedidos.infrastructure.messages.PedidoRecebidoMessage
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.core.RabbitTemplate
import java.math.BigDecimal

@ExtendWith(MockKExtension::class)
class NotificacaoGatewayImplTest {

    private val queuePedidoCriado = Queue("pedido-criado", false)

    private val queuePedidoRecebido = Queue("pedido-recebido", false)

    private val queuePedidoAlterado = Queue("pedido-alterado", false)

    @MockK
    private lateinit var rabbitTemplate: RabbitTemplate

    private lateinit var notificacaoGatewayImpl: NotificacaoGatewayImpl

    @BeforeEach
    fun init() {
        notificacaoGatewayImpl =
            NotificacaoGatewayImpl(queuePedidoCriado, queuePedidoRecebido, queuePedidoAlterado, rabbitTemplate)
    }

    @Test
    fun `deve notificar pedido criado`() {
        val pedido = criarPedido()

        every { rabbitTemplate.convertAndSend(eq(queuePedidoCriado.name), any<PedidoCriadoMessage>()) } returns Unit
        every { rabbitTemplate.convertAndSend(eq(queuePedidoAlterado.name), any<PedidoAlteradoMessage>()) } returns Unit

        assertDoesNotThrow { notificacaoGatewayImpl.notificarPedidoCriado(pedido) }
    }

    @Test
    fun `deve propagar erro ao notificar pedido criado`() {
        val pedido = criarPedido()

        every {
            rabbitTemplate.convertAndSend(eq(queuePedidoCriado.name), any<PedidoCriadoMessage>())
        } returns Unit
        every {
            rabbitTemplate.convertAndSend(eq(queuePedidoAlterado.name), any<PedidoAlteradoMessage>())
        } throws Exception("Error")

        val exception = Assertions.assertThrows(IntegracaoAPIException::class.java) {
            notificacaoGatewayImpl.notificarPedidoCriado(pedido)
        }

        Assertions.assertEquals("Erro ao notificar pedido criado. Detalhes: Error", exception.message)

        verify(exactly = 1) {
            rabbitTemplate.convertAndSend(eq(queuePedidoCriado.name), any<PedidoCriadoMessage>())
        }
        verify(exactly = 1) {
            rabbitTemplate.convertAndSend(eq(queuePedidoAlterado.name), any<PedidoAlteradoMessage>())
        }
    }

    @Test
    fun `deve notificar pedido aprovado`() {
        val pedido = criarPedido()

        every { rabbitTemplate.convertAndSend(eq(queuePedidoRecebido.name), any<PedidoRecebidoMessage>()) } returns Unit
        every { rabbitTemplate.convertAndSend(eq(queuePedidoAlterado.name), any<PedidoAlteradoMessage>()) } returns Unit

        assertDoesNotThrow { notificacaoGatewayImpl.notificarPedidoAlterado(pedido) }
    }

    @Test
    fun `deve notificar pedido recebido`() {
        val pedido = criarPedido()
        pedido.status = PedidoStatus.RECEBIDO

        every { rabbitTemplate.convertAndSend(eq(queuePedidoRecebido.name), any<PedidoRecebidoMessage>()) } returns Unit
        every { rabbitTemplate.convertAndSend(eq(queuePedidoAlterado.name), any<PedidoAlteradoMessage>()) } returns Unit

        assertDoesNotThrow { notificacaoGatewayImpl.notificarPedidoAlterado(pedido) }
    }

    @Test
    fun `deve propagar erro ao notificar pedido aprovado`() {
        val pedido = criarPedido()

        every {
            rabbitTemplate.convertAndSend(eq(queuePedidoRecebido.name), any<PedidoRecebidoMessage>())
        } returns Unit
        every {
            rabbitTemplate.convertAndSend(eq(queuePedidoAlterado.name), any<PedidoAlteradoMessage>())
        } throws Exception("Error")

        val exception = Assertions.assertThrows(IntegracaoAPIException::class.java) {
            notificacaoGatewayImpl.notificarPedidoAlterado(pedido)
        }

        Assertions.assertEquals("Erro ao notificar pedido aprovado. Detalhes: Error", exception.message)

        verify(exactly = 1) {
            rabbitTemplate.convertAndSend(eq(queuePedidoRecebido.name), any<PedidoRecebidoMessage>())
        }
        verify(exactly = 1) {
            rabbitTemplate.convertAndSend(eq(queuePedidoAlterado.name), any<PedidoAlteradoMessage>())
        }
    }

    private fun criarPedido() = Pedido(
        id = 1,
        numero = "1",
        clienteId = "1",
        pagamentoId = "1",
        status = PedidoStatus.APROVADO,
        statusPagamento = PagamentoStatus.APROVADO,
        items = listOf(
            Item(
                id = 1,
                quantidade = 1,
                comentario = "Sem mostarda",
                valorPago = BigDecimal(10),
                produto = Produto(
                    id = 1,
                    nome = "Produto 1",
                    descricao = "descricao",
                    categoria = Categoria.BEBIDA,
                    valor = BigDecimal(10),
                    tempoPreparo = 10,
                    disponivel = true,
                    excluido = false,
                    imagem = Imagem(1, "/caminho.jpg")
                )
            )
        )
    )
}