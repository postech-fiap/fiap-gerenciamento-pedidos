package br.com.fiap.gerenciamentopedidos.infrastructure.adapters

import br.com.fiap.gerenciamentopedidos.domain.dtos.ProdutoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.models.Produto
import br.com.fiap.gerenciamentopedidos.infrastructure.entities.ProdutoEntity
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.ProdutoJpaRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
class ProdutoMySqlAdapterTest {

    @MockK
    lateinit var repository: ProdutoJpaRepository

    @InjectMockKs
    lateinit var adapter: ProdutoMySqlAdapter

    @Test
    fun `deve buscar um produto por id com sucesso`() {
        //given
        val id = 1L
        val produto = Produto(
            id = id,
            nome = "Nome",
            descricao = null,
            categoria = Categoria.BEBIDA,
            valor = 1.0,
            tempoPreparo = 1,
            disponivel = true,
            excluido = false,
            imagem = null
        )
        val dto = ProdutoDto.fromModel(produto)

        every { repository.findById(any()) } returns Optional.of(ProdutoEntity.fromDto(dto))

        //when
        val result = adapter.get(id)

        //then
        Assertions.assertEquals(ProdutoDto.fromModel(produto), result.get())
        verify(exactly = 1) { repository.findById(any()) }
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
            valor = 1.0,
            tempoPreparo = 1,
            disponivel = true,
            excluido = false,
            imagem = null
        )
        val dto = ProdutoDto.fromModel(produto)
        every { repository.findByCategoria(any()) } returns listOf(ProdutoEntity.fromDto(dto))

        //when
        val result = adapter.get(categoria)

        //then
        Assertions.assertEquals(ProdutoDto.fromModel(produto), result.first())
        verify(exactly = 1) { repository.findByCategoria(categoria) }
    }

    @Test
    fun `deve salvar um produto com sucesso`() {
        //given
        val produto = Produto(
            id = 1L,
            nome = "Nome",
            descricao = null,
            categoria = Categoria.BEBIDA,
            valor = 1.0,
            tempoPreparo = 1,
            disponivel = true,
            excluido = false,
            imagem = null
        )
        val dto = ProdutoDto.fromModel(produto)
        val entity = ProdutoEntity.fromDto(dto)

        every { repository.save(any()) } returns entity

        //when
        val result = adapter.create(ProdutoDto.fromModel(produto))

        //then
        Assertions.assertEquals(ProdutoDto.fromModel(produto), result)
        verify(exactly = 1) { repository.save(entity) }
    }

    @Test
    fun `deve atualizar um produto com sucesso`() {
        //given
        val produto = Produto(
            id = 1L,
            nome = "Nome",
            descricao = null,
            categoria = Categoria.BEBIDA,
            valor = 1.0,
            tempoPreparo = 1,
            disponivel = true,
            excluido = false,
            imagem = null
        )
        val dto = ProdutoDto.fromModel(produto)
        val entity = ProdutoEntity.fromDto(dto)

        every { repository.findById(any()) } returns Optional.of(entity)
        every { repository.save(any()) } returns entity

        //when
        val result = adapter.update(ProdutoDto.fromModel(produto))

        //then
        Assertions.assertEquals(ProdutoDto.fromModel(produto), result)
        verify(exactly = 1) { repository.save(entity) }
    }
}