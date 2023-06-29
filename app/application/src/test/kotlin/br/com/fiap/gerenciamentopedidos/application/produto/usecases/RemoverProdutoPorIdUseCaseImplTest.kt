package br.com.fiap.gerenciamentopedidos.application.produto.usecases

import br.com.fiap.gerenciamentopedidos.domain.cadastro.exceptions.BusinessException
import br.com.fiap.gerenciamentopedidos.domain.cadastro.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.interfaces.repositories.ProdutoRepository
import br.com.fiap.gerenciamentopedidos.domain.models.Produto
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
class RemoverProdutoPorIdUseCaseImplTest {
    @InjectMockKs
    lateinit var useCase: RemoverProdutoPorIdUseCaseImpl

    @MockK
    lateinit var repository: ProdutoRepository

    @Test
    fun `deve obter produto por id com sucesso`() {
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

        every { repository.get(id) } returns Optional.of(produto)
        every { repository.update(produto) } returns produto

        //when
        Assertions.assertDoesNotThrow { useCase.executar(id) }

        //then
        verify(exactly = 1) { repository.get(id) }
        verify(exactly = 1) { repository.update(produto) }
    }

    @Test
    fun `deve lançar exceção por produto já excluído`() {
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
            excluido = true,
            imagem = null
        )

        every { repository.get(id) } returns Optional.of(produto)

        //when
        val exception = Assertions.assertThrows(BusinessException::class.java) {
            useCase.executar(id)
        }

        //then
        Assertions.assertEquals("Produto já está excluído", exception.message)
        verify(exactly = 1) { repository.get(id) }
        verify(exactly = 0) { repository.update(produto) }
    }

    @Test
    fun `deve lançar exceção por não encontrar produto`() {
        //given
        val id = 1L

        every { repository.get(id) } returns Optional.empty()

        //when
        val exception = Assertions.assertThrows(RecursoNaoEncontradoException::class.java) {
            useCase.executar(id)
        }

        //then
        Assertions.assertEquals("Produto não encontrado", exception.message)

        verify(exactly = 1) { repository.get(id) }
    }
}
