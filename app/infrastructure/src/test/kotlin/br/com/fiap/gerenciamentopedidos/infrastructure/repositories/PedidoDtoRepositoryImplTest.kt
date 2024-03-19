package br.com.fiap.gerenciamentopedidos.infrastructure.repositories

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.models.Item
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import br.com.fiap.gerenciamentopedidos.domain.models.Produto
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
import java.math.BigDecimal
import java.util.*
import kotlin.random.Random

@ExtendWith(MockKExtension::class)
class PedidoDtoRepositoryImplTest {
    @MockK
    lateinit var pedidoJpaRepository: PedidoJpaRepository

    @InjectMockKs
    lateinit var pedidoRepository: PedidoRepositoryImpl

    @Test
    fun `deve buscar pedido por referencia com sucesso`() {
        val pedido = Pedido(
            id = 1,
            numero = "1",
            statusPagamento = PagamentoStatus.APROVADO,
            items = listOf(
                Item(
                    produto = Produto(
                        id = 1L,
                        nome = "Nome",
                        descricao = null,
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

        every { pedidoJpaRepository.findById(any()) } returns Optional.of(PedidoEntity.fromModel(pedido))

        val result = pedidoRepository.buscarPedidoPorId(1L)

        assertEquals(pedido.id, result.get().id)

        verify(exactly = 1) { pedidoJpaRepository.findById(any()) }
    }

    @Test
    fun `deve propagar erro ao buscar pedido por referencia`() {
        val errorMessage = "Erro ao obter pedido por id. Detalhes: Error"
        every { pedidoJpaRepository.findById(any()) } throws Exception("Error")

        val exception =
            Assertions.assertThrows(RuntimeException::class.java) { pedidoRepository.buscarPedidoPorId(1L) }

        assertEquals(errorMessage, exception.message)
        verify(exactly = 1) { pedidoJpaRepository.findById(any()) }
    }

    @Test
    fun `deve salvar um pedido com sucesso`() {
        //given
        val pedido = Pedido(
            id = 1,
            numero = "1",
            statusPagamento = PagamentoStatus.APROVADO,
            items = listOf(
                Item(
                    produto = Produto(
                        id = 1L,
                        nome = "Nome",
                        descricao = null,
                        categoria = Categoria.BEBIDA,
                        valor = BigDecimal.valueOf(1.0),
                        tempoPreparo = 1,
                        disponivel = true,
                        excluido = false,
                        imagem = null
                    ),
                    quantidade = 1,
                    valorPago = Random.nextLong().toBigDecimal()
                )
            ),
        )

        pedido.valorTotal = null
        val entity = PedidoEntity.fromModel(pedido)

        every { pedidoJpaRepository.save(any()) } returns entity

        //when
        val result = pedidoRepository.salvar(pedido)

        //then
        assertEquals(pedido, result)
        verify(exactly = 1) { pedidoJpaRepository.save(any()) }
    }

    @Test
    fun `deve propagar erro ao  deve salvar um pedido`() {
        val errorMessage = "Erro ao salvar pedido. Detalhes: Error"

        val pedido = Pedido(
            id = 1,
            numero = "1",
            statusPagamento = PagamentoStatus.APROVADO,
            items = listOf(
                Item(
                    produto = Produto(
                        id = 1L,
                        nome = "Nome",
                        descricao = null,
                        categoria = Categoria.BEBIDA,
                        valor = BigDecimal.valueOf(1.0),
                        tempoPreparo = 1,
                        disponivel = true,
                        excluido = false,
                        imagem = null
                    ),
                    quantidade = 1,
                    valorPago = Random.nextLong().toBigDecimal()
                )
            ),
        )

        every { pedidoJpaRepository.save(any()) } throws Exception("Error")

        //when
        val exception = Assertions.assertThrows(BaseDeDadosException::class.java) {
            pedidoRepository.salvar(pedido)
        }

        //then
        assertEquals(errorMessage, exception.message)
    }

    @Test
    fun `deve obter ultimo pedido do dia com sucesso`() {
        every { pedidoJpaRepository.obterUltimoNumeroPedidoDoDia() } returns "10"

        val result = pedidoRepository.obterUltimoNumeroPedidoDoDia()

        assertEquals("10", result)
    }

    @Test
    fun `deve propagar erro ao obter ultimo pedido do dia`() {
        val errorMessage = "Erro ao obter próximo número pedido. Detalhes: Error"

        every { pedidoJpaRepository.obterUltimoNumeroPedidoDoDia() } throws Exception("Error")

        val exception = Assertions.assertThrows(BaseDeDadosException::class.java) {
            pedidoRepository.obterUltimoNumeroPedidoDoDia()
        }

        assertEquals(errorMessage, exception.message)
    }

    @Test
    fun `deve atualizar o status do pedido com sucesso`() {
        // given
        val pedido = Pedido(
            id = 1,
            numero = "1",
            statusPagamento = PagamentoStatus.APROVADO,
            items = listOf(
                Item(
                    produto = Produto(
                        id = 1L,
                        nome = "Nome",
                        descricao = null,
                        categoria = Categoria.BEBIDA,
                        valor = BigDecimal.valueOf(1.0),
                        tempoPreparo = 1,
                        disponivel = true,
                        excluido = false,
                        imagem = null
                    ),
                    quantidade = 1,
                    valorPago = Random.nextLong().toBigDecimal()
                )
            ),
        )
        pedido.valorTotal = null
        val entity = PedidoEntity.fromModel(pedido)

        every { pedidoJpaRepository.findById(any()) } returns Optional.of(entity)
        every { pedidoJpaRepository.save(any()) } returns entity

        // when
        pedidoRepository.update(pedido)

        // then
        verify(exactly = 1) { pedidoJpaRepository.save(any()) }
    }

    @Test
    fun `deve propagar erro ao alterar status do pedido`() {
        val errorMessage = "Erro ao atualizar Pedido. Detalhes: Error"
        val pedido = Pedido(
            id = 1,
            numero = "1",
            statusPagamento = PagamentoStatus.APROVADO,
            items = listOf(
                Item(
                    produto = Produto(
                        id = 1L,
                        nome = "Nome",
                        descricao = null,
                        categoria = Categoria.BEBIDA,
                        valor = BigDecimal.valueOf(1.0),
                        tempoPreparo = 1,
                        disponivel = true,
                        excluido = false,
                        imagem = null
                    ),
                    quantidade = 1,
                    valorPago = Random.nextLong().toBigDecimal()
                )
            ),
        )

        every { pedidoJpaRepository.findById(any()) } returns Optional.of(PedidoEntity.fromModel(pedido))
        every { pedidoJpaRepository.save(any()) } throws Exception("Error")

        val exception = Assertions.assertThrows(BaseDeDadosException::class.java) { pedidoRepository.update(pedido) }

        assertEquals(errorMessage, exception.message)
    }
}
