package br.com.fiap.gerenciamentopedidos.infrastructure.gateways

import br.com.fiap.gerenciamentopedidos.domain.dtos.MercadoPagoResponseOrdemDto
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.models.Item
import br.com.fiap.gerenciamentopedidos.domain.models.Pagamento
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import br.com.fiap.gerenciamentopedidos.domain.models.Produto
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import java.math.BigDecimal
import java.time.OffsetDateTime
import kotlin.random.Random

private const val MERCADO_PAGO_QR_CODE_ENDPOINT = "mercadoPagoApiGenerateQrcodeEndpoint"
private const val MERCADO_PAGO_TOKEN = "mercadoPagoToken"

@ExtendWith(MockKExtension::class)
class GerarQrCodePagamentoHttpGatewayImplTest {

    lateinit var gerarQrCodePagamentoGateway: GerarQrCodePagamentoHttpGatewayImpl

    @MockK
    lateinit var restTemplate: RestTemplate

    @BeforeEach
    fun init() {
        gerarQrCodePagamentoGateway = GerarQrCodePagamentoHttpGatewayImpl(
            restTemplate,
            MERCADO_PAGO_QR_CODE_ENDPOINT,
            MERCADO_PAGO_TOKEN
        )
    }

    @Test
    fun `deve gerar o pagamento pendente com qrcode`() {
        //given
        val pedido = Pedido(
            id = 1,
            numero = "1",
            valorTotal = Random.nextLong().toBigDecimal(),
            pagamento = Pagamento(
                1,
                OffsetDateTime.now(),
                PagamentoStatus.PENDENTE,
                Random.nextLong().toString(),
                Random.nextLong().toBigDecimal()
            ),
            items = listOf(
                Item(
                    produto = Produto(
                        id = 1L,
                        nome = "Nome",
                        descricao = "Description",
                        categoria = Categoria.BEBIDA,
                        valor = BigDecimal.valueOf(1.0),
                        tempoPreparo = 1,
                        disponivel = true,
                        excluido = false,
                        imagem = null
                    ),
                    quantidade = 1,
                    valorPago = Random.nextLong().toBigDecimal()
                ),
            ),
        )

        every {
            restTemplate.postForEntity(
                eq(MERCADO_PAGO_QR_CODE_ENDPOINT),
                any(),
                eq(MercadoPagoResponseOrdemDto::class.java)
            )
        } returns ResponseEntity(
            MercadoPagoResponseOrdemDto(Random.nextLong().toString(), Random.nextLong().toString()),
            HttpStatus.CREATED
        )

        //when
        val pagamentoComQrCode = gerarQrCodePagamentoGateway.executar(pedido)

        //then
        Assertions.assertNotNull(pagamentoComQrCode.qrCode)
        Assertions.assertNotNull(pagamentoComQrCode.valorTotal)
        Assertions.assertNotNull(pagamentoComQrCode.dataHora)
        Assertions.assertEquals(PagamentoStatus.PENDENTE, pagamentoComQrCode.status)

        verify(exactly = 1) {
            restTemplate.postForEntity(
                eq(MERCADO_PAGO_QR_CODE_ENDPOINT),
                any(),
                eq(MercadoPagoResponseOrdemDto::class.java)
            )
        }
    }

    @Test
    fun `deve lancar um erro quando a integracao de gerar o pagamento falhar`() {
        //given
        val pedido = Pedido(
            id = 1,
            numero = "1",
            valorTotal = Random.nextLong().toBigDecimal(),
            pagamento = Pagamento(
                1, OffsetDateTime.now(), PagamentoStatus.PENDENTE,
                qrCode = Random.nextLong().toString(), valorTotal = Random.nextLong().toBigDecimal()
            ),
            items = listOf(
                Item(
                    produto = Produto(
                        id = 1L,
                        nome = "Nome",
                        descricao = "Description",
                        categoria = Categoria.BEBIDA,
                        valor = BigDecimal.valueOf(1.0),
                        tempoPreparo = 1,
                        disponivel = true,
                        excluido = false,
                        imagem = null
                    ),
                    quantidade = 1,
                    valorPago = Random.nextLong().toBigDecimal()
                ),
            ),
        )

        every {
            restTemplate.postForEntity(
                eq(MERCADO_PAGO_QR_CODE_ENDPOINT),
                any(),
                eq(MercadoPagoResponseOrdemDto::class.java)
            )
        } throws Exception("Error")

        val errorMessageExpected = "Erro de integração para gerar o pagamento. Detalhes: Error - null"

        //when-then
        val exception = Assertions.assertThrows(RuntimeException::class.java) {
            gerarQrCodePagamentoGateway.executar(pedido)
        }

        //then
        Assertions.assertEquals(errorMessageExpected, exception.message)

        verify(exactly = 1) {
            restTemplate.postForEntity(
                eq(MERCADO_PAGO_QR_CODE_ENDPOINT),
                any(),
                eq(MercadoPagoResponseOrdemDto::class.java)
            )
        }

    }

    @Test
    fun `deve lancar um erro quando o retorno da integracao for diferente de 201`() {
        //given
        val pedido = Pedido(
            id = 1,
            numero = "1",
            valorTotal = Random.nextLong().toBigDecimal(),
            pagamento = Pagamento(
                1, OffsetDateTime.now(), PagamentoStatus.PENDENTE,
                qrCode = Random.nextLong().toString(), valorTotal = Random.nextLong().toBigDecimal()
            ),
            items = listOf(
                Item(
                    produto = Produto(
                        id = 1L,
                        nome = "Nome",
                        descricao = "Description",
                        categoria = Categoria.BEBIDA,
                        valor = BigDecimal.valueOf(1.0),
                        tempoPreparo = 1,
                        disponivel = true,
                        excluido = false,
                        imagem = null
                    ),
                    quantidade = 1,
                    valorPago = Random.nextLong().toBigDecimal()
                ),
            ),
        )

        every {
            restTemplate.postForEntity(
                eq(MERCADO_PAGO_QR_CODE_ENDPOINT),
                any(),
                eq(MercadoPagoResponseOrdemDto::class.java)
            )
        } returns ResponseEntity(
            MercadoPagoResponseOrdemDto(Random.nextLong().toString(), Random.nextLong().toString()),
            HttpStatus.CONFLICT
        )

        val errorMessageExpected = "Erro de integração para gerar o pagamento. Detalhes: [status_code: 409 CONFLICT"

        //when-then
        val exception = Assertions.assertThrows(RuntimeException::class.java) {
            gerarQrCodePagamentoGateway.executar(pedido)
        }

        //then
        Assertions.assertEquals(errorMessageExpected, exception.message)

        verify(exactly = 1) {
            restTemplate.postForEntity(
                eq(MERCADO_PAGO_QR_CODE_ENDPOINT),
                any(),
                eq(MercadoPagoResponseOrdemDto::class.java)
            )
        }
    }
}