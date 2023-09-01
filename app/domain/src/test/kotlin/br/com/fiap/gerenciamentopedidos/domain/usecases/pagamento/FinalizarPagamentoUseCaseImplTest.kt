package br.com.fiap.gerenciamentopedidos.domain.usecases.pagamento

import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus.APROVADO
import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus.REPROVADO
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PagamentoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.gateways.BuscarPagamentoPorIdGateway
import br.com.fiap.gerenciamentopedidos.domain.models.*
import br.com.fiap.gerenciamentopedidos.domain.models.mercadoPago.MerchantOrders
import br.com.fiap.gerenciamentopedidos.domain.models.mercadoPago.PagamentoCriado
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
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
        val ordemDePagamento = criarOrdemDePagamento(random, "approved")
        val idDoPedido = ordemDePagamento.obterIdDoPedido()!!.toLong()

        every { buscarPagamentoPorIdGateway.executar(pagamentoCriado.data.id) } returns ordemDePagamento
        every { pagamentoRepository.alterarStatusPagamento(APROVADO, idDoPedido) } returns Unit
        every { pedidoRepository.alterarStatusPedido(PedidoStatus.RECEBIDO, idDoPedido) } returns Unit

        //when
        val result = finalizarPagamentoUseCaseImpl.executar(pagamentoCriado)

        //then
        verify(exactly = 1) { buscarPagamentoPorIdGateway.executar(pagamentoCriado.data.id) }
        verify(exactly = 1) { pagamentoRepository.alterarStatusPagamento(APROVADO, idDoPedido) }
        verify(exactly = 1) { pedidoRepository.alterarStatusPedido(PedidoStatus.RECEBIDO, idDoPedido) }

        Assertions.assertNotNull(result)
        Assertions.assertEquals(APROVADO.toString(), result.status)
    }

    @Test
    fun `deve finalizar o pagamento com status REPROVADO e o pedido com status PENDENTE quando o pagamento nao foi aprovado`() {
        //given
        val random = Random.nextLong()
        val pagamentoCriado = criarPagamento()
        val ordemDePagamento = criarOrdemDePagamento(random, "REJECTED")
        val idDoPedido = ordemDePagamento.obterIdDoPedido()!!.toLong()

        every { buscarPagamentoPorIdGateway.executar(pagamentoCriado.data.id) } returns ordemDePagamento
        every { pagamentoRepository.alterarStatusPagamento(REPROVADO, idDoPedido) } returns Unit
        every { pedidoRepository.alterarStatusPedido(PedidoStatus.PENDENTE, idDoPedido) } returns Unit

        //when
        val result = finalizarPagamentoUseCaseImpl.executar(pagamentoCriado)

        //then
        verify(exactly = 1) { buscarPagamentoPorIdGateway.executar(pagamentoCriado.data.id) }
        verify(exactly = 1) { pagamentoRepository.alterarStatusPagamento(REPROVADO, idDoPedido) }
        verify(exactly = 1) { pedidoRepository.alterarStatusPedido(PedidoStatus.PENDENTE, idDoPedido) }

        Assertions.assertNotNull(result)
        Assertions.assertEquals(REPROVADO.toString(), result.status)
    }

    private fun criarOrdemDePagamento(random: Long, statusPagamento: String) : MerchantOrders {
        val payments = listOf(
                MerchantOrders.Elements.Payment(
                        id = random,
                        transactionAmount = BigDecimal.TEN,
                        totalPaidAmount = BigDecimal.TEN,
                        status = statusPagamento,
                        statusDetail = random.toString(),
                        dateApproved = random.toString(),
                        dateCreated = random.toString(),
                        lastModified = random.toString(),
                        amountRefunded = BigDecimal.ZERO
                )
        )

        val elements = listOf(
                MerchantOrders.Elements(
                        random,
                        random.toString(),
                        random.toString(),
                        payments
                )
        )

        return MerchantOrders(elements = elements)
    }

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