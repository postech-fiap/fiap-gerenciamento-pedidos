package br.com.fiap.gerenciamentopedidos.infrastructure.gateways

import br.com.fiap.gerenciamentopedidos.domain.dtos.ItemPedidoDto
import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoDto
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import java.time.OffsetDateTime

private const val PRODUCAO_URL = "producaoEndpoint"

@ExtendWith(MockKExtension::class)
class ProducaoGatewayImplTest {

    @MockK
    lateinit var restTemplate: RestTemplate

    lateinit var producaoGateway: ProducaoGatewayImpl

    @BeforeEach
    fun init() {
        producaoGateway = ProducaoGatewayImpl(PRODUCAO_URL, restTemplate)
    }

    @Test
    fun `deve enviar pedido para producao com sucesso`() {
        val param = criarPedidoDto()

        every {
            restTemplate.postForEntity(eq("${PRODUCAO_URL}/order"), any(), eq(PedidoDto::class.java))
        } returns ResponseEntity(param, HttpStatus.CREATED)


        val result = producaoGateway.enviar(param)

        assertNotNull(result)
    }

    private fun criarPedidoDto(): PedidoDto {
        return PedidoDto(
            1,
            "1",
            OffsetDateTime.now(),
            listOf(ItemPedidoDto("name", 1, "comentário"))
        )
    }

}