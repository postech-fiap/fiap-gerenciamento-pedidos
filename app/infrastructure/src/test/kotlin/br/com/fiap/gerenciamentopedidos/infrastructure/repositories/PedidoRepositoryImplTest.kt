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
import kotlin.random.Random

@ExtendWith(MockKExtension::class)
class PedidoRepositoryImplTest {

    @MockK
    lateinit var pedidoJpaRepository: PedidoJpaRepository

    @InjectMockKs
    lateinit var pedidoRepository: PedidoRepositoryImpl

    @Test
    fun `deve buscar pedidos com sucesso`() {
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
                ),
            ),
        )

        pedido.valorTotal = null
        val pedidoList = listOf(pedido)

        val pedidoEntity = PedidoEntity.fromModel(pedido)
        val pedidoEntityList = listOf(pedidoEntity)

        every {
            pedidoJpaRepository.buscarPedidos()
        } returns pedidoEntityList

        // when
        val result = pedidoRepository.buscarPedidos()

        // then
        assertEquals(pedidoList, result)

        verify(exactly = 1) {
            pedidoJpaRepository.buscarPedidos()
        }
    }

    @Test
    fun `deve propagar erro ao buscar pedidos`() {
        // given
        val errorMessage = "Erro ao listar pedidos por categoria. Detalhes: Error"

        every {
            pedidoJpaRepository.buscarPedidos()
        } throws Exception("Error")

        // when-then
        val exception = Assertions.assertThrows(RuntimeException::class.java) {
            pedidoRepository.buscarPedidos()
        }

        // then
        assertEquals(errorMessage, exception.message)

        verify(exactly = 1) {
            pedidoJpaRepository.buscarPedidos()
        }
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

        every {
            pedidoJpaRepository.updateStatusById(pedido.status, pedido.id!!)
        } returns Unit

        // when
        pedidoRepository.alterarStatusPedido(pedido.status, pedido.id!!)

        // then
        verify(exactly = 1) {
            pedidoJpaRepository.updateStatusById(pedido.status, pedido.id!!)
        }
    }

}
