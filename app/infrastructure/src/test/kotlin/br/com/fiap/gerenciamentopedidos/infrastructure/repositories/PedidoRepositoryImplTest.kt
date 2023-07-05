package br.com.fiap.gerenciamentopedidos.infrastructure.repositories

import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import br.com.fiap.gerenciamentopedidos.infrastructure.entities.PedidoEntity
import br.com.fiap.gerenciamentopedidos.infrastructure.exceptions.BaseDeDadosException
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.jpa.PedidoJpaRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.OffsetDateTime

@ExtendWith(MockKExtension::class)
class PedidoRepositoryImplTest {

    @MockK
    lateinit var pedidoJpaRepository: PedidoJpaRepository

    @InjectMockKs
    lateinit var pedidoRepository: PedidoRepositoryImpl

    @Test
    fun `deve buscar pedidos com sucesso`() {
        // given
        val pedido = Pedido(1, OffsetDateTime.now(), PedidoStatus.RECEBIDO, 10, "1234", null, null, null)
        val dto = PedidoDto.fromModel(pedido)
        val pedidoList = listOf(dto)

        val pedidoEntity = PedidoEntity.fromDto(dto)
        val pedidoEntityList = listOf(pedidoEntity)

        val status = PedidoStatus.RECEBIDO
        val dataInicial = OffsetDateTime.now().minusHours(24)
        val dataFinal = OffsetDateTime.now()

        every {
            pedidoJpaRepository.findByStatusAndDataHoraGreaterThanEqualAndDataHoraLessThanEqual(
                status,
                dataInicial,
                dataFinal
            )
        } returns pedidoEntityList

        // when
        val result = pedidoRepository.buscarPedidos(status, dataInicial, dataFinal)

        // then
        assertEquals(pedidoList, result)

        verify(exactly = 1) {
            pedidoJpaRepository.findByStatusAndDataHoraGreaterThanEqualAndDataHoraLessThanEqual(
                status,
                dataInicial,
                dataFinal
            )
        }
    }

    @Test
    fun `deve propagar erro ao buscar pedidos`() {
        // given
        val status = PedidoStatus.RECEBIDO
        val dataInicial = OffsetDateTime.now().minusHours(24)
        val dataFinal = OffsetDateTime.now()
        val errorMessage = "Erro ao buscar pedidos na base de dados. Detalhes: Error"

        every {
            pedidoJpaRepository.findByStatusAndDataHoraGreaterThanEqualAndDataHoraLessThanEqual(
                status,
                dataInicial,
                dataFinal
            )
        } throws Exception("Error")

        // when-then
        val exception = Assertions.assertThrows(BaseDeDadosException::class.java) {
            pedidoRepository.buscarPedidos(status, dataInicial, dataFinal)
        }

        // then
        assertEquals(errorMessage, exception.message)

        verify(exactly = 1) {
            pedidoJpaRepository.findByStatusAndDataHoraGreaterThanEqualAndDataHoraLessThanEqual(
                status,
                dataInicial,
                dataFinal
            )
        }
    }

    @Test
    fun `deve atualizar o status do pedido com sucesso`() {

        // given
        val pedido = Pedido(1, OffsetDateTime.now(), PedidoStatus.RECEBIDO, 10, "1234", null, null, null)
        val dto = PedidoDto.fromModel(pedido).copy(status = PedidoStatus.EM_PREPARACAO)

        val pedidoEntity = PedidoEntity.fromDto(dto)

        every {
            pedidoJpaRepository.save(pedidoEntity)
        } returns pedidoEntity

        // when
        val result = pedidoRepository.alterarStatusPedido(dto)

        // then
        assertEquals(dto, result)

        verify(exactly = 1) {
            pedidoJpaRepository.save(pedidoEntity)
        }

    }

}
