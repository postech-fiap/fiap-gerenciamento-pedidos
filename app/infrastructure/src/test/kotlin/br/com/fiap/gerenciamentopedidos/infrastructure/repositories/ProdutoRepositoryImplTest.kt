package br.com.fiap.gerenciamentopedidos.infrastructure.repositories

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.models.Imagem
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
    fun `deve propagar erro ao buscar produto por id`() {
        val errorMessage = "Erro ao obter produto por id. Detalhes: Error"
        val id = 1L
        every { produtoJpaRepository.findByIdAndExcluidoFalse(id) } throws RuntimeException("Error")

        val exception = Assertions.assertThrows(BaseDeDadosException::class.java) { produtoRepository.get(id) }

        Assertions.assertEquals(errorMessage, exception.message)
        verify(exactly = 1) { produtoJpaRepository.findByIdAndExcluidoFalse(id) }
    }

    @Test
    fun `deve listar produtos por ids com sucesso`() {
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

        every {
            produtoJpaRepository.findByIdInAndExcluidoFalseAndDisponivelTrue(any())
        } returns listOf(ProdutoEntity.fromModel(produto))

        val result = produtoRepository.get(listOf(id))

        Assertions.assertEquals(produto.id, result[0].id)
        verify(exactly = 1) { produtoJpaRepository.findByIdInAndExcluidoFalseAndDisponivelTrue(any()) }
    }

    @Test
    fun `deve propagar erro ao listar produtos por ids`() {
        val errorMessage = "Erro ao obter produto por id. Detalhes: Error"
        val ids = listOf(1L)
        every { produtoJpaRepository.findByIdInAndExcluidoFalseAndDisponivelTrue(ids) } throws RuntimeException("Error")

        val exception = Assertions.assertThrows(BaseDeDadosException::class.java) { produtoRepository.get(ids) }

        Assertions.assertEquals(errorMessage, exception.message)
        verify(exactly = 1) { produtoJpaRepository.findByIdInAndExcluidoFalseAndDisponivelTrue(ids) }
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
    fun `deve propagar erro ao buscar um produtos por categoria`() {
        val errorMessage = "Erro ao listar produtos por categoria. Detalhes: Error"
        val categoria = Categoria.BEBIDA
        every {
            produtoJpaRepository.findByCategoriaAndExcluidoAndDisponivel(categoria, excluido = false, disponivel = true)
        } throws RuntimeException("Error")

        val exception = Assertions.assertThrows(BaseDeDadosException::class.java) { produtoRepository.get(categoria) }

        Assertions.assertEquals(errorMessage, exception.message)
        verify(exactly = 1) {
            produtoJpaRepository.findByCategoriaAndExcluidoAndDisponivel(
                categoria,
                excluido = false,
                disponivel = true
            )
        }
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
    fun `deve propagar erro ao salvar um produto`() {
        val errorMessage = "Erro ao salvar produto. Detalhes: Error"
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
        every { produtoJpaRepository.save(any()) } throws RuntimeException("Error")

        val exception = Assertions.assertThrows(BaseDeDadosException::class.java) { produtoRepository.create(produto) }

        Assertions.assertEquals(errorMessage, exception.message)
        verify(exactly = 1) { produtoJpaRepository.save(any()) }
    }

    @Test
    fun `deve atualizar um produto com sucesso`() {
        //given
        val produto = Produto(
            id = 1L,
            nome = "Nome",
            categoria = Categoria.BEBIDA,
            valor = BigDecimal.valueOf(1.0),
            tempoPreparo = 1,
            disponivel = true,
            excluido = false
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
    fun `deve atualizar um produto com sucesso - imagem nula na base`() {
        //given
        val produto = Produto(
            id = 1L,
            nome = "Nome",
            categoria = Categoria.BEBIDA,
            valor = BigDecimal.valueOf(1.0),
            tempoPreparo = 1,
            disponivel = true,
            excluido = false,
            imagem = Imagem(1, "url")
        )
        val entity = ProdutoEntity.fromModel(
            Produto(
                id = 1L,
                nome = "Nome",
                categoria = Categoria.BEBIDA,
                valor = BigDecimal.valueOf(1.0),
                tempoPreparo = 1,
                disponivel = true,
                excluido = false,
            )
        )

        every { produtoJpaRepository.findByIdAndExcluidoFalse(any()) } returns Optional.of(entity)
        every { produtoJpaRepository.save(any()) } returns ProdutoEntity.fromModel(produto)

        //when
        val result = produtoRepository.update(produto)

        //then
        Assertions.assertEquals(produto, result)
        verify(exactly = 1) { produtoJpaRepository.save(any()) }
    }

    @Test
    fun `deve atualizar um produto com sucesso - remover imagem`() {
        //given
        val produto = Produto(
            id = 1L,
            nome = "Nome",
            categoria = Categoria.BEBIDA,
            valor = BigDecimal.valueOf(1.0),
            tempoPreparo = 1,
            disponivel = true,
            excluido = false
        )
        val entity = ProdutoEntity.fromModel(
            Produto(
                id = 1L,
                nome = "Nome",
                categoria = Categoria.BEBIDA,
                valor = BigDecimal.valueOf(1.0),
                tempoPreparo = 1,
                disponivel = true,
                excluido = false,
                imagem = Imagem(1, "url")
            )
        )

        every { produtoJpaRepository.findByIdAndExcluidoFalse(any()) } returns Optional.of(entity)
        every { produtoJpaRepository.save(any()) } returns ProdutoEntity.fromModel(produto)

        //when
        val result = produtoRepository.update(produto)

        //then
        Assertions.assertEquals(produto, result)
        verify(exactly = 1) { produtoJpaRepository.save(any()) }
    }

    @Test
    fun `deve atualizar um produto com sucesso - atualizar imagem`() {
        //given
        val produto = Produto(
            id = 1L,
            nome = "Nome",
            categoria = Categoria.BEBIDA,
            valor = BigDecimal.valueOf(1.0),
            tempoPreparo = 1,
            disponivel = true,
            excluido = false,
            imagem = Imagem(1, "url1")
        )
        val entity = ProdutoEntity.fromModel(
            Produto(
                id = 1L,
                nome = "Nome",
                categoria = Categoria.BEBIDA,
                valor = BigDecimal.valueOf(1.0),
                tempoPreparo = 1,
                disponivel = true,
                excluido = false,
                imagem = Imagem(1, "url2")
            )
        )

        every { produtoJpaRepository.findByIdAndExcluidoFalse(any()) } returns Optional.of(entity)
        every { produtoJpaRepository.save(any()) } returns ProdutoEntity.fromModel(produto)

        //when
        val result = produtoRepository.update(produto)

        //then
        Assertions.assertEquals(produto, result)
        verify(exactly = 1) { produtoJpaRepository.save(any()) }
    }

    @Test
    fun `deve propagar erro ao atualizar um produto`() {
        val errorMessage = "Erro ao atualizar produto. Detalhes: Error"
        val produto = Produto(
            id = 1L,
            nome = "Nome",
            categoria = Categoria.BEBIDA,
            valor = BigDecimal.valueOf(1.0),
            tempoPreparo = 1,
            disponivel = true,
            excluido = false
        )
        val entity = ProdutoEntity.fromModel(produto)

        every { produtoJpaRepository.findByIdAndExcluidoFalse(any()) } returns Optional.of(entity)
        every { produtoJpaRepository.save(any()) } throws RuntimeException("Error")

        val exception = Assertions.assertThrows(BaseDeDadosException::class.java) { produtoRepository.update(produto) }

        Assertions.assertEquals(errorMessage, exception.message)
        verify(exactly = 1) { produtoJpaRepository.findByIdAndExcluidoFalse(any()) }
        verify(exactly = 1) { produtoJpaRepository.save(any()) }
    }

    @Test
    fun `deve propagar erro ao atualizar um produto - produto nao encontrado`() {
        val errorMessage = "Erro ao atualizar produto. Detalhes: Produto não encontrado"
        val produto = Produto(
            id = 1L,
            nome = "Nome",
            categoria = Categoria.BEBIDA,
            valor = BigDecimal.valueOf(1.0),
            tempoPreparo = 1,
            disponivel = true,
            excluido = false
        )
        every { produtoJpaRepository.findByIdAndExcluidoFalse(any()) } returns Optional.empty()

        val exception = Assertions.assertThrows(BaseDeDadosException::class.java) { produtoRepository.update(produto) }

        Assertions.assertEquals(errorMessage, exception.message)
        verify(exactly = 1) { produtoJpaRepository.findByIdAndExcluidoFalse(any()) }
        verify(exactly = 0) { produtoJpaRepository.save(any()) }
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