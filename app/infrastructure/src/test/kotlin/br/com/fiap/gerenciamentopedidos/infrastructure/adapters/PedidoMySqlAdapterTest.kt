package br.com.fiap.gerenciamentopedidos.infrastructure.adapters

import br.com.fiap.gerenciamentopedidos.domain.cadastro.exceptions.BaseDeDadosException
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.pedido.models.*
import br.com.fiap.gerenciamentopedidos.infrastructure.entities.PedidoEntity
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.PedidoJpaRepository
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
class PedidoMySqlAdapterTest {

    @MockK
    lateinit var pedidoJpaRepository: PedidoJpaRepository

    @InjectMockKs
    lateinit var pedidoMySqlAdapter: PedidoMySqlAdapter

    @Test
    fun `deve buscar pedidos com sucesso`() {
        // given
        val pedido = Pedido(1, OffsetDateTime.now(), PedidoStatus.PENDENTE, 10, "1234", null, null, null)
        val pedidoDomainList = listOf(pedido)

        val pedidoEntity = PedidoEntity.fromDomain(pedido)
        val pedidoEntityList = listOf(pedidoEntity)

        val status = PedidoStatus.PENDENTE
        val dataInicial = OffsetDateTime.now().minusHours(24)
        val dataFinal = OffsetDateTime.now()

        every { pedidoJpaRepository.findByStatusAndDataHoraGreaterThanEqualAndDataHoraLessThanEqual(
            status,
            dataInicial,
            dataFinal)
        } returns pedidoEntityList

        // when
        val result = pedidoMySqlAdapter.buscarPedidos(status, dataInicial, dataFinal)

        // then
        assertEquals(pedidoDomainList, result)

        verify(exactly = 1) { pedidoJpaRepository.findByStatusAndDataHoraGreaterThanEqualAndDataHoraLessThanEqual(
            status,
            dataInicial,
            dataFinal
        ) }
    }

    @Test
    fun `deve propagar erro ao buscar pedidos`() {
        // given
        val status = PedidoStatus.PENDENTE
        val dataInicial = OffsetDateTime.now().minusHours(24)
        val dataFinal = OffsetDateTime.now()
        val errorMessage = "Erro ao buscar pedidos na base de dados. Detalhes: Error"

        every { pedidoJpaRepository.findByStatusAndDataHoraGreaterThanEqualAndDataHoraLessThanEqual(
            status,
            dataInicial,
            dataFinal
        ) } throws Exception("Error")

        // when-then
        val exception = Assertions.assertThrows(BaseDeDadosException::class.java) {
            pedidoMySqlAdapter.buscarPedidos(status, dataInicial, dataFinal)
        }

        // then
        assertEquals(errorMessage, exception.message)

        verify(exactly = 1) { pedidoJpaRepository.findByStatusAndDataHoraGreaterThanEqualAndDataHoraLessThanEqual(
            status,
            dataInicial,
            dataFinal
        ) }
    }

}
