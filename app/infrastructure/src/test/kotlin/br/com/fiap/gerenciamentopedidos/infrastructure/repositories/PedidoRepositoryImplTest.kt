package br.com.fiap.gerenciamentopedidos.infrastructure.repositories

import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.models.Pagamento
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import br.com.fiap.gerenciamentopedidos.domain.models.PedidoProduto
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
        val pedido = Pedido(
            id = 1,
            numero = "1",
            pagamento = Pagamento(1, OffsetDateTime.now(), PagamentoStatus.APROVADO),
            produtos = listOf(
                PedidoProduto(
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
                )
            ),
        )
        val dto = PedidoDto.fromModel(pedido)
        val pedidoList = listOf(dto)

        val pedidoEntity = PedidoEntity.fromDto(dto)
        val pedidoEntityList = listOf(pedidoEntity)

        val status = PedidoStatus.RECEBIDO
        val dataInicial = OffsetDateTime.now().minusHours(24)
        val dataFinal = OffsetDateTime.now()

        every {
            pedidoJpaRepository.findByStatusAndDataHoraGreaterThanEqualAndDataHoraLessThanEqualOrderByDataHoraDesc(
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
            pedidoJpaRepository.findByStatusAndDataHoraGreaterThanEqualAndDataHoraLessThanEqualOrderByDataHoraDesc(
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
        val errorMessage = "Erro ao listar pedidos por categoria. Detalhes: Error"

        every {
            pedidoJpaRepository.findByStatusAndDataHoraGreaterThanEqualAndDataHoraLessThanEqualOrderByDataHoraDesc(
                status,
                dataInicial,
                dataFinal
            )
        } throws Exception("Error")

        // when-then
        val exception = Assertions.assertThrows(RuntimeException::class.java) {
            pedidoRepository.buscarPedidos(status, dataInicial, dataFinal)
        }

        // then
        assertEquals(errorMessage, exception.message)

        verify(exactly = 1) {
            pedidoJpaRepository.findByStatusAndDataHoraGreaterThanEqualAndDataHoraLessThanEqualOrderByDataHoraDesc(
                status,
                dataInicial,
                dataFinal
            )
        }
    }

    @Test
    fun `deve salvar um pedido com sucesso`() {
        //given
        val pedido = Pedido(
            id = 1,
            numero = "1",
            pagamento = Pagamento(1, OffsetDateTime.now(), PagamentoStatus.APROVADO),
            produtos = listOf(
                PedidoProduto(
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
                )
            ),
        )
        val dto = PedidoDto.fromModel(pedido)
        val entity = PedidoEntity.fromDto(dto)

        every { pedidoJpaRepository.save(any()) } returns entity

        //when
        val result = pedidoRepository.salvar(dto)

        //then
        assertEquals(dto, result)
        verify(exactly = 1) { pedidoJpaRepository.save(any()) }
    }

    @Test
    fun `deve propagar erro ao  deve salvar um pedido`() {
        val errorMessage = "Erro ao salvar pedido. Detalhes: Error"

        val pedido = Pedido(
            id = 1,
            numero = "1",
            pagamento = Pagamento(1, OffsetDateTime.now(), PagamentoStatus.APROVADO),
            produtos = listOf(
                PedidoProduto(
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
                )
            ),
        )

        every { pedidoJpaRepository.save(any()) } throws Exception("Error")

        //when
        val exception = Assertions.assertThrows(BaseDeDadosException::class.java) {
            pedidoRepository.salvar(PedidoDto.fromModel(pedido))
        }

        //then
        assertEquals(errorMessage, exception.message)
    }

    @Test
    fun `deve obter ultimo pedido do dia com sucesso`() {
        every { pedidoJpaRepository.obterUtimoNumeroPedidoDoDia() } returns "10"

        val result = pedidoRepository.obterUltimoNumeroPedidoDoDia()

        assertEquals("10", result)
    }

    @Test
    fun `deve propagar erro ao obter ultimo pedido do dia`() {
        val errorMessage = "Erro ao obter próximo número pedido. Detalhes: Error"

        every { pedidoJpaRepository.obterUtimoNumeroPedidoDoDia() } throws Exception("Error")

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
            pagamento = Pagamento(1, OffsetDateTime.now(), PagamentoStatus.APROVADO),
            produtos = listOf(
                PedidoProduto(
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
                )
            ),
        )
        val dto = PedidoDto.fromModel(pedido).copy(status = PedidoStatus.EM_PREPARACAO)

        every {
            pedidoJpaRepository.updateStatusById(dto.status!!, dto.id!!)
        } returns Unit

        // when
        val result = pedidoRepository.alterarStatusPedido(dto.status!!, dto.id!!)

        // then

        verify(exactly = 1) {
            pedidoJpaRepository.updateStatusById(dto.status!!, dto.id!!)
        }
    }

}
