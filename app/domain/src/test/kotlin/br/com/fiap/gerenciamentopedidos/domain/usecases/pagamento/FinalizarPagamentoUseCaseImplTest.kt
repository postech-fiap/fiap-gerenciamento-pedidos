package br.com.fiap.gerenciamentopedidos.domain.usecases.pagamento

import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus.APROVADO
import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus.REPROVADO
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PagamentoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.gateways.BuscarPagamentoPorIdGateway
import br.com.fiap.gerenciamentopedidos.domain.models.*
import br.com.fiap.gerenciamentopedidos.domain.models.mercadoPago.DetalhePagamento
import br.com.fiap.gerenciamentopedidos.domain.models.mercadoPago.PagamentoCriado
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.random.Random

@ExtendWith(MockKExtension::class)
class FinalizarPagamentoUseCaseImplTest {

    @MockK
    lateinit var buscarPagamentoPorIdGateway: BuscarPagamentoPorIdGateway

    @MockK
    lateinit var pagamentoRepository: PagamentoRepository

    @MockK
    lateinit var pedidoRepository: PedidoRepository

    @InjectMockKs
    lateinit var finalizarPagamentoUseCaseImpl: FinalizarPagamentoUseCaseImpl

    @Test
    fun `deve finalizar o pagamento com status APROVADO e o pedido com status RECEBIDO quando o pagamento foi aprovado`() {
        //given
        val random = Random.nextLong()
        val pagamentoCriado = criarPagamento()
        val detalhePagamento = criarDetalhePagamento(random, "approved")

        every { buscarPagamentoPorIdGateway.executar(pagamentoCriado.data.id) } returns detalhePagamento
        every { pagamentoRepository.alterarStatusPagamento(APROVADO, detalhePagamento.externalReference.toLong()) } returns Unit
        every { pedidoRepository.alterarStatusPedido(PedidoStatus.RECEBIDO, detalhePagamento.externalReference.toLong()) } returns Unit

        //when
        val result = finalizarPagamentoUseCaseImpl.executar(pagamentoCriado)

        //then
        verify(exactly = 1) { buscarPagamentoPorIdGateway.executar(pagamentoCriado.data.id) }
        verify(exactly = 1) { pagamentoRepository.alterarStatusPagamento(APROVADO, detalhePagamento.externalReference.toLong()) }
        verify(exactly = 1) { pedidoRepository.alterarStatusPedido(PedidoStatus.RECEBIDO, detalhePagamento.externalReference.toLong()) }

        Assertions.assertNotNull(result)
        Assertions.assertEquals(APROVADO.toString(), result.status)
    }

    @Test
    fun `deve finalizar o pagamento com status REPROVADO e o pedido com status PENDENTE quando o pagamento nao foi aprovado`() {
        //given
        val random = Random.nextLong()
        val pagamentoCriado = criarPagamento()
        val detalhePagamento = criarDetalhePagamento(random, "rejected")

        every { buscarPagamentoPorIdGateway.executar(pagamentoCriado.data.id) } returns detalhePagamento
        every { pagamentoRepository.alterarStatusPagamento(REPROVADO, detalhePagamento.externalReference.toLong()) } returns Unit
        every { pedidoRepository.alterarStatusPedido(PedidoStatus.PENDENTE, detalhePagamento.externalReference.toLong()) } returns Unit

        //when
        val result = finalizarPagamentoUseCaseImpl.executar(pagamentoCriado)

        //then
        verify(exactly = 1) { buscarPagamentoPorIdGateway.executar(pagamentoCriado.data.id) }
        verify(exactly = 1) { pagamentoRepository.alterarStatusPagamento(REPROVADO, detalhePagamento.externalReference.toLong()) }
        verify(exactly = 1) { pedidoRepository.alterarStatusPedido(PedidoStatus.PENDENTE, detalhePagamento.externalReference.toLong()) }

        Assertions.assertNotNull(result)
        Assertions.assertEquals(REPROVADO.toString(), result.status)
    }

    private fun criarDetalhePagamento(random: Long, status: String) = DetalhePagamento(
            id = random,
            dateCreated = random.toString(),
            dateApproved = random.toString(),
            dateLastUpdated = random.toString(),
            status = status,
            externalReference = random.toString()
    )

    private fun criarPagamento(): PagamentoCriado {
        val random = Random.nextLong()
        val pagamentoCriado = PagamentoCriado(
                action = random.toString(),
                apiVersion = random.toString(),
                data = PagamentoCriado.Data(random.toString()),
                dateCreated = random.toString(),
                id = random,
                liveMode = true,
                type = "payment",
                userId = random.toString()
        )

        return pagamentoCriado
    }
}