package br.com.fiap.gerenciamentopedidos.domain.usecases.pedido

import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoDto
import br.com.fiap.gerenciamentopedidos.domain.dtos.requests.BuscarPedidosRequest
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.exceptions.BaseDeDadosException
import br.com.fiap.gerenciamentopedidos.domain.ports.drivens.PedidoPort
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
    lateinit var buscarUseCaseImpl: BuscarPedidosImpl

    @MockK
    lateinit var pedidoPort: PedidoPort

    @Test
    fun `deve retornar um pedido`() {
        // given
        val pedido = PedidoDto(1, OffsetDateTime.now(), PedidoStatus.PENDENTE, 10, "1234", null, null, null)
        val status = PedidoStatus.PENDENTE
        val dataInicial = OffsetDateTime.now().minusHours(24)
        val dataFinal = OffsetDateTime.now()
        val request = BuscarPedidosRequest(status, dataInicial, dataFinal)

        every { pedidoPort.buscarPedidos(status, dataInicial, dataFinal) } returns listOf(pedido)

        // when
        val result = buscarUseCaseImpl.executar(request)

        // then
        Assertions.assertEquals(pedido.id, result.first().id)

        verify(exactly = 1) { pedidoPort.buscarPedidos(status, dataInicial, dataFinal) }
    }

    @Test
    fun `deve propagar erro quando ocorrer falha ao buscar pedidos`() {
        // given
        val status = PedidoStatus.PENDENTE
        val dataInicial = OffsetDateTime.now().minusHours(24)
        val dataFinal = OffsetDateTime.now()
        val request = BuscarPedidosRequest(status, dataInicial, dataFinal)
        val errorMessage = "Erro na base de dados"

        every { pedidoPort.buscarPedidos(status, dataInicial, dataFinal) } throws BaseDeDadosException(errorMessage)

        // when-then
        val exception = Assertions.assertThrows(BaseDeDadosException::class.java) {
            buscarUseCaseImpl.executar(request)
        }

        Assertions.assertEquals(errorMessage, exception.message)

        verify(exactly = 1) { pedidoPort.buscarPedidos(status, dataInicial, dataFinal) }
    }
}
