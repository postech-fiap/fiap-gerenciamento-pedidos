package br.com.fiap.gerenciamentopedidos.infrastructure.repositories

import br.com.fiap.gerenciamentopedidos.domain.dtos.ProdutoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.models.Produto
import br.com.fiap.gerenciamentopedidos.infrastructure.entities.ProdutoEntity
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
        val dto = ProdutoDto.fromModel(produto)

        every { produtoJpaRepository.findById(any()) } returns Optional.of(ProdutoEntity.fromDto(dto))

        //when
        val result = produtoRepository.get(id)

        //then
        Assertions.assertEquals(ProdutoDto.fromModel(produto), result.get())
        verify(exactly = 1) { produtoJpaRepository.findById(any()) }
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
        val dto = ProdutoDto.fromModel(produto)
        every { produtoJpaRepository.findByCategoriaAndExcluidoAndDisponivel(any(), any(), any()) } returns listOf(
            ProdutoEntity.fromDto(dto)
        )

        //when
        val result = produtoRepository.get(categoria)

        //then
        Assertions.assertEquals(ProdutoDto.fromModel(produto), result.first())
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
            valor = BigDecimal.valueOf( 1.0),
            tempoPreparo = 1,
            disponivel = true,
            excluido = false,
            imagem = null
        )
        val dto = ProdutoDto.fromModel(produto)
        val entity = ProdutoEntity.fromDto(dto)

        every { produtoJpaRepository.save(any()) } returns entity

        //when
        val result = produtoRepository.create(ProdutoDto.fromModel(produto))

        //then
        Assertions.assertEquals(ProdutoDto.fromModel(produto), result)
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
            valor = BigDecimal.valueOf( 1.0),
            tempoPreparo = 1,
            disponivel = true,
            excluido = false,
            imagem = null
        )
        val dto = ProdutoDto.fromModel(produto)
        val entity = ProdutoEntity.fromDto(dto)

        every { produtoJpaRepository.findById(any()) } returns Optional.of(entity)
        every { produtoJpaRepository.save(any()) } returns entity

        //when
        val result = produtoRepository.update(ProdutoDto.fromModel(produto))

        //then
        Assertions.assertEquals(ProdutoDto.fromModel(produto), result)
        verify(exactly = 1) { produtoJpaRepository.save(entity) }
    }
}