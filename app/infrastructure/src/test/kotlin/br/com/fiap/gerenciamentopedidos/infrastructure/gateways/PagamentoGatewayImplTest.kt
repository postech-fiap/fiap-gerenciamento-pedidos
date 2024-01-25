package br.com.fiap.gerenciamentopedidos.infrastructure.gateways

import br.com.fiap.gerenciamentopedidos.domain.dtos.ItemPagamentoDto
import br.com.fiap.gerenciamentopedidos.domain.dtos.PagamentoDto
import br.com.fiap.gerenciamentopedidos.domain.dtos.ProdutoPagamentoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.infrastructure.exceptions.IntegracaoAPIException
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import java.math.BigDecimal
import java.time.OffsetDateTime

private const val PAGAMENTO_URL = "pagamentoEndpoint"

@ExtendWith(MockKExtension::class)
class PagamentoGatewayImplTest {
    @MockK
    lateinit var restTemplate: RestTemplate

    private lateinit var pagamentoGateway: PagamentoGatewayImpl

    @BeforeEach
    fun init() {
        pagamentoGateway = PagamentoGatewayImpl(PAGAMENTO_URL, restTemplate)
    }

    @Test
    fun `deve realizar pagamento com sucesso`() {
        val param = criarPagamentoDto()

        every {
            restTemplate.postForEntity(eq("${PAGAMENTO_URL}/v1/pagamentos/criar"), any(), eq(PagamentoDto::class.java))
        } returns ResponseEntity(param, HttpStatus.CREATED)

        val result = pagamentoGateway.criar(param)

        Assertions.assertNotNull(result)
    }

    @Test
    fun `deve propagar erro ao realizar pagamento`() {
        val errorMessage = "Erro de integração para gerar o pagamento. Detalhes: [status_code: 400 BAD_REQUEST"
        val param = criarPagamentoDto()

        every {
            restTemplate.postForEntity(eq("${PAGAMENTO_URL}/v1/pagamentos/criar"), any(), eq(PagamentoDto::class.java))
        } returns ResponseEntity(param, HttpStatus.BAD_REQUEST)

        val exception = Assertions.assertThrows(IntegracaoAPIException::class.java) { pagamentoGateway.criar(param) }

        Assertions.assertEquals(errorMessage, exception.message)
    }

    @Test
    fun `deve propagar erro ao executar api de pagamento`() {
        val errorMessage = "Erro de integração para gerar o pagamento. Detalhes: java.lang.Exception: Error"
        val param = criarPagamentoDto()

        every {
            restTemplate.postForEntity(eq("${PAGAMENTO_URL}/v1/pagamentos/criar"), any(), eq(PagamentoDto::class.java))
        } throws Exception(Exception("Error"))

        val exception = Assertions.assertThrows(IntegracaoAPIException::class.java) { pagamentoGateway.criar(param) }

        Assertions.assertEquals(errorMessage, exception.message)
    }

    private fun criarPagamentoDto(): PagamentoDto {
        return PagamentoDto(
            referenciaPedido = "sadsdasd",
            numeroPedido = "213",
            dataHora = OffsetDateTime.now(),
            valorTotal = BigDecimal(1),
            status = PagamentoStatus.PENDENTE,
            items = listOf(
                ItemPagamentoDto(
                    quantidade = 1,
                    valorPago = BigDecimal(1),
                    produto = ProdutoPagamentoDto(
                        id = 1,
                        nome = "dasdsda",
                        descricao = "sadasd",
                        categoria = "LANCHE",
                        valor = BigDecimal(1)
                    )
                )
            )
        )
    }
}