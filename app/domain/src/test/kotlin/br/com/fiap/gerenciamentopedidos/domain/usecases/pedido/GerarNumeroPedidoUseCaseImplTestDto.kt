package br.com.fiap.gerenciamentopedidos.domain.usecases.pedido

import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class GerarNumeroPedidoUseCaseImplTestDto {

    @MockK
    lateinit var pedidoRepository: PedidoRepository

    @InjectMockKs
    lateinit var gerarNumeroPedidoUseCase: GerarNumeroPedidoUseCaseImpl

    @Test
    fun `deve gerar um numero pedido com sucesso`() {
        // Arrange
        every { pedidoRepository.obterUltimoNumeroPedidoDoDia() } returns "1"

        // Act
        val result = gerarNumeroPedidoUseCase.executar()

        // Assert
        assertEquals("2", result)

        verify(exactly = 1) { pedidoRepository.obterUltimoNumeroPedidoDoDia() }
    }

    @Test
    fun `deve gerar um numero de primeiro pedido com sucesso`() {
        // Arrange
        every { pedidoRepository.obterUltimoNumeroPedidoDoDia() } returns "0"

        // Act
        val result = gerarNumeroPedidoUseCase.executar()

        // Assert
        assertEquals("1", result)

        verify(exactly = 1) { pedidoRepository.obterUltimoNumeroPedidoDoDia() }
    }
}
