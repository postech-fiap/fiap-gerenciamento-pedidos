package br.com.fiap.gerenciamentopedidos.infrastructure.repositories

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.models.Produto
import br.com.fiap.gerenciamentopedidos.infrastructure.entities.ProdutoEntity
import br.com.fiap.gerenciamentopedidos.infrastructure.exceptions.BaseDeDadosException
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.jpa.ProdutoJpaRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.util.*

@ExtendWith(MockKExtension::class)
class ProdutoRepositoryImplTest {

    @MockK
    lateinit var produtoJpaRepository: ProdutoJpaRepository

    @InjectMockKs
    lateinit var produtoRepository: ProdutoRepositoryImpl

    @Test
    fun `deve buscar um produto por id com sucesso`() {
        //given
        val id = 1L
        val produto = Produto(
            id = id,
            nome = "Nome",
            descricao = null,
            categoria = Categoria.BEBIDA,
            valor = BigDecimal.valueOf(1.0),
            tempoPreparo = 1,
            disponivel = true,
            excluido = false,
            imagem = null
        )

        every { produtoJpaRepository.findByIdAndExcluidoFalse(any()) } returns Optional.of(
            ProdutoEntity.fromModel(
                produto
            )
        )

        //when
        val result = produtoRepository.get(id)

        //then
        Assertions.assertEquals(produto, result.get())
        verify(exactly = 1) { produtoJpaRepository.findByIdAndExcluidoFalse(any()) }
    }

    @Test
    fun `deve buscar um produtos por categoria com sucesso`() {
        //given
        val categoria = Categoria.BEBIDA
        val produto = Produto(
            id = 1L,
            nome = "Nome",
            descricao = null,
            categoria = categoria,
            valor = BigDecimal.valueOf(1.0),
            tempoPreparo = 1,
            disponivel = true,
            excluido = false,
            imagem = null
        )

        every { produtoJpaRepository.findByCategoriaAndExcluidoAndDisponivel(any(), any(), any()) } returns listOf(
            ProdutoEntity.fromModel(produto)
        )

        //when
        val result = produtoRepository.get(categoria)

        //then
        Assertions.assertEquals(produto, result.first())
        verify(exactly = 1) { produtoJpaRepository.findByCategoriaAndExcluidoAndDisponivel(any(), any(), any()) }
    }

    @Test
    fun `deve salvar um produto com sucesso`() {
        //given
        val produto = Produto(
            id = 1L,
            nome = "Nome",
            descricao = null,
            categoria = Categoria.BEBIDA,
            valor = BigDecimal.valueOf(1.0),
            tempoPreparo = 1,
            disponivel = true,
            excluido = false,
            imagem = null
        )
        val entity = ProdutoEntity.fromModel(produto)

        every { produtoJpaRepository.save(any()) } returns entity

        //when
        val result = produtoRepository.create(produto)

        //then
        Assertions.assertEquals(produto, result)
        verify(exactly = 1) { produtoJpaRepository.save(entity) }
    }

    @Test
    fun `deve atualizar um produto com sucesso`() {
        //given
        val produto = Produto(
            id = 1L,
            nome = "Nome",
            descricao = null,
            categoria = Categoria.BEBIDA,
            valor = BigDecimal.valueOf(1.0),
            tempoPreparo = 1,
            disponivel = true,
            excluido = false,
            imagem = null
        )
        val entity = ProdutoEntity.fromModel(produto)

        every { produtoJpaRepository.findByIdAndExcluidoFalse(any()) } returns Optional.of(entity)
        every { produtoJpaRepository.save(any()) } returns entity

        //when
        val result = produtoRepository.update(produto)

        //then
        Assertions.assertEquals(produto, result)
        verify(exactly = 1) { produtoJpaRepository.save(entity) }
    }

    @Test
    fun `deve excluir um produto com sucesso`() {
        //given
        val id = 1L
        every { produtoJpaRepository.deleteById(id) } returns Unit

        //when
        Assertions.assertDoesNotThrow { produtoRepository.remove(id) }

        //then
        verify(exactly = 1) { produtoJpaRepository.deleteById(id) }
    }

    @Test
    fun `deve propagar erro ao exluir produto`() {
        //given
        val errorMessage = "Erro ao excluir produto. Detalhes: Error"
        val id = 1L
        every { produtoRepository.remove(id) } throws RuntimeException("Error")

        //when
        val exception = Assertions.assertThrows(BaseDeDadosException::class.java) {
            produtoRepository.remove(id)
        }

        //then
        Assertions.assertEquals(errorMessage, exception.message)

        verify(exactly = 1) { produtoJpaRepository.deleteById(id) }
    }
}