package br.com.fiap.gerenciamentopedidos.application.usecases.pedido

import br.com.fiap.gerenciamentopedidos.application.requests.AlterarStatusPedidoRequest
import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.exceptions.BusinessException
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.OffsetDateTime

@ExtendWith(MockKExtension::class)
class AlterarStatusPedidoUseCaseImplTest {

    @InjectMockKs
    lateinit var useCase: AlterarStatusPedidoUseCaseImpl

    @MockK
    lateinit var pedidoPort: PedidoRepository

    @Test
    fun `deve alterar o status do pedido para EM_PREPARACAO com Sucesso`() {
        //given
        val pedidoId = 1L

        val pedido = Pedido(pedidoId, OffsetDateTime.now(), PedidoStatus.RECEBIDO, 10, "1234", null, null, null)
        val pedidoDto = PedidoDto.fromModel(pedido)
        val copyPedido = pedidoDto.copy(status = PedidoStatus.EM_PREPARACAO)
        val alterarStatusPedidoRequest = AlterarStatusPedidoRequest(pedidoId, PedidoStatus.EM_PREPARACAO)

        every { pedidoPort.buscarPedidoPorId(pedidoId) } returns pedidoDto
        every { pedidoPort.alterarStatusPedido(copyPedido) } returns copyPedido

        //when
        val result = useCase.executar(alterarStatusPedidoRequest)

        //then

        assertEquals(PedidoStatus.EM_PREPARACAO, result.status)

        verify(exactly = 1) { pedidoPort.buscarPedidoPorId(pedidoId) }
        verify(exactly = 1) { pedidoPort.alterarStatusPedido(copyPedido) }

    }


    @Test
    fun `nao deve alterar o status do pedido para RECEBIDO porque o ja possui esse status`() {
        //given
        val pedidoId = 1L
        val errorMessage = "O status do pedido ja está igual à RECEBIDO"
        val pedido = Pedido(pedidoId, OffsetDateTime.now(), PedidoStatus.RECEBIDO, 10, "1234", null, null, null)
        val pedidoDto = PedidoDto.fromModel(pedido)
        val copyPedido = pedidoDto.copy(status = PedidoStatus.RECEBIDO)
        val alterarStatusPedidoRequest = AlterarStatusPedidoRequest(pedidoId, PedidoStatus.RECEBIDO)

        every { pedidoPort.buscarPedidoPorId(pedidoId) } returns pedidoDto
        every { pedidoPort.alterarStatusPedido(copyPedido) } returns copyPedido

        //when
        val exception = assertThrows(BusinessException::class.java) {
            useCase.executar(alterarStatusPedidoRequest)
        }

        //then
        assertEquals(exception.message, errorMessage)

        verify(exactly = 1) { pedidoPort.buscarPedidoPorId(pedidoId) }
        verify(exactly = 0) { pedidoPort.alterarStatusPedido(copyPedido) }

    }

}