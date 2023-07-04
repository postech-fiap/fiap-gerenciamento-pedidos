package br.com.fiap.gerenciamentopedidos.application.usecases.pedido

import br.com.fiap.gerenciamentopedidos.application.requests.BuscarPedidosRequest
import br.com.fiap.gerenciamentopedidos.application.responses.PedidoResponse
import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoDto
import br.com.fiap.gerenciamentopedidos.domain.exceptions.BaseDeDadosException
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.ports.PedidoPort
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.OffsetDateTime

@ExtendWith(MockKExtension::class)
class BuscarPedidosUseCaseTest {

    @InjectMockKs
    lateinit var buscarUseCaseImpl: BuscarUseCaseImpl

    @MockK
    lateinit var pedidoPort: PedidoPort

    @Test
    fun `deve retornar um pedido`() {
        // given
        val pedido = Pedido(1, OffsetDateTime.now(), PedidoStatus.PENDENTE, 10, "1234", null, null, null)
        val pedidoList = listOf(PedidoDto.fromModel(pedido))

        val status = PedidoStatus.PENDENTE
        val dataInicial = OffsetDateTime.now().minusHours(24)
        val dataFinal = OffsetDateTime.now()
        val buscarPedidosRequest = BuscarPedidosRequest(status, dataInicial, dataFinal)

        every { pedidoPort.buscarPedidos(status, dataInicial, dataFinal) } returns pedidoList

        // when
        val result = buscarUseCaseImpl.executar(buscarPedidosRequest)

        // then
        Assertions.assertEquals(pedidoList.map { PedidoResponse(it) }, result)

        verify(exactly = 1) { pedidoPort.buscarPedidos(status, dataInicial, dataFinal) }
    }

    @Test
    fun `deve propagar erro quando ocorrer falha ao buscar pedidos`() {
        // given
        val status = PedidoStatus.PENDENTE
        val dataInicial = OffsetDateTime.now().minusHours(24)
        val dataFinal = OffsetDateTime.now()
        val buscarPedidosRequest = BuscarPedidosRequest(status, dataInicial, dataFinal)

        val errorMessage = "Erro na base de dados"

        every { pedidoPort.buscarPedidos(status, dataInicial, dataFinal) } throws BaseDeDadosException(errorMessage)

        // when-then
        val exception = Assertions.assertThrows(BaseDeDadosException::class.java) {
            buscarUseCaseImpl.executar(buscarPedidosRequest)
        }

        Assertions.assertEquals(errorMessage, exception.message)

        verify(exactly = 1) { pedidoPort.buscarPedidos(status, dataInicial, dataFinal) }
    }

}
