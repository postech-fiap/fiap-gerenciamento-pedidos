package br.com.fiap.gerenciamentopedidos.infrastructure.gateways

import br.com.fiap.gerenciamentopedidos.domain.dtos.MercadoPagoResponseMerchantOrders
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import kotlin.random.Random

private const val MERCADO_PAGO_PAGAMENTO_ENDPOINT = "mercadoPagoApiPagamentoEndpoint"
private const val MERCADO_PAGO_TOKEN = "mercadoPagoToken"

@ExtendWith(MockKExtension::class)
class BuscarPagamentoPorIdHttpGatewayImplTest {

    lateinit var buscarPagamentoPorIdGateway: BuscarPagamentoPorIdHttpGatewayImpl

    @MockK
    lateinit var restTemplate: RestTemplate

    @BeforeEach
    fun init() {
        buscarPagamentoPorIdGateway = BuscarPagamentoPorIdHttpGatewayImpl(
            restTemplate,
            MERCADO_PAGO_PAGAMENTO_ENDPOINT,
            MERCADO_PAGO_TOKEN
        )
    }

    @Test
    fun `deve buscar ordem de pagamento com sucesso`() {
        //given
        val random = Random.nextLong()
        val id = random.toString()

        every {
            restTemplate.exchange(
                eq(MERCADO_PAGO_PAGAMENTO_ENDPOINT),
                eq(HttpMethod.GET),
                any(),
                eq(MercadoPagoResponseMerchantOrders::class.java)
            )
        } returns ResponseEntity(
            MercadoPagoResponseMerchantOrders(
                elements = listOf()
            ),
            HttpStatus.OK
        )

        //when
        val pagamento = buscarPagamentoPorIdGateway.executar(id)

        //then
        Assertions.assertNotNull(pagamento)

        verify(exactly = 1) {
            restTemplate.exchange(
                eq(MERCADO_PAGO_PAGAMENTO_ENDPOINT),
                eq(HttpMethod.GET),
                any(),
                eq(MercadoPagoResponseMerchantOrders::class.java)
            )
        }
    }

    @Test
    fun `deve lancar um erro quando a integracao de buscar a ordem de pagamento falhar`() {
        //given
        val random = Random.nextLong()
        val id = random.toString()

        every {
            restTemplate.exchange(
                eq(MERCADO_PAGO_PAGAMENTO_ENDPOINT),
                eq(HttpMethod.GET),
                any(),
                eq(MercadoPagoResponseMerchantOrders::class.java)
            )
        } throws Exception("Error")

        val errorMessageExpected = "Erro de integração para buscar o pagamento. Detalhes: Error - null"

        //when-then
        val exception = Assertions.assertThrows(RuntimeException::class.java) {
            buscarPagamentoPorIdGateway.executar(id)
        }

        //then
        Assertions.assertEquals(errorMessageExpected, exception.message)

        verify(exactly = 1) {
            restTemplate.exchange(
                eq(MERCADO_PAGO_PAGAMENTO_ENDPOINT),
                eq(HttpMethod.GET),
                any(),
                eq(MercadoPagoResponseMerchantOrders::class.java)
            )
        }

    }

    @Test
    fun `deve lancar um erro quando o retorno da integracao for diferente de 200`() {
        //given
        val random = Random.nextLong()
        val id = random.toString()

        every {
            restTemplate.exchange(
                eq(MERCADO_PAGO_PAGAMENTO_ENDPOINT),
                eq(HttpMethod.GET),
                any(),
                eq(MercadoPagoResponseMerchantOrders::class.java)
            )
        } returns ResponseEntity(
            MercadoPagoResponseMerchantOrders(
                elements = listOf()
            ),
            HttpStatus.CONFLICT
        )

        val errorMessageExpected = "Erro de integração para buscar o pagamento. Detalhes: [status_code: 409 CONFLICT"

        //when-then
        val exception = Assertions.assertThrows(RuntimeException::class.java) {
            buscarPagamentoPorIdGateway.executar(id)
        }

        //then
        Assertions.assertEquals(errorMessageExpected, exception.message)

        verify(exactly = 1) {
            restTemplate.exchange(
                eq(MERCADO_PAGO_PAGAMENTO_ENDPOINT),
                eq(HttpMethod.GET),
                any(),
                eq(MercadoPagoResponseMerchantOrders::class.java)
            )
        }
    }
}