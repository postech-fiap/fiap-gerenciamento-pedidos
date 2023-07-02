package br.com.fiap.gerenciamentopedidos.domain.usecases.produto

import br.com.fiap.gerenciamentopedidos.domain.dtos.ProdutoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.exceptions.BusinessException
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.ports.drivens.ProdutoPort
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
    lateinit var produtoPort: ProdutoPort

    @Test
    fun `deve remover produto por id com sucesso`() {
        //given
        val id = 1L
        val produto = ProdutoDto(
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

        every { produtoPort.get(id) } returns Optional.of(produto)
        every { produtoPort.update(any()) } returns produto

        //when
        Assertions.assertDoesNotThrow { useCase.executar(id) }

        //then
        verify(exactly = 1) { produtoPort.get(id) }
        verify(exactly = 1) { produtoPort.update(any()) }
    }

    @Test
    fun `deve lançar exceção por produto já excluído`() {
        //given
        val id = 1L
        val produto = ProdutoDto(
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

        every { produtoPort.get(id) } returns Optional.of(produto)

        //when
        val exception = Assertions.assertThrows(BusinessException::class.java) {
            useCase.executar(id)
        }

        //then
        Assertions.assertEquals("Produto já está excluído", exception.message)
        verify(exactly = 1) { produtoPort.get(id) }
        verify(exactly = 0) { produtoPort.update(produto) }
    }

    @Test
    fun `deve lançar exceção por não encontrar produto`() {
        //given
        val id = 1L

        every { produtoPort.get(id) } returns Optional.empty()

        //when
        val exception = Assertions.assertThrows(RecursoNaoEncontradoException::class.java) {
            useCase.executar(id)
        }

        //then
        Assertions.assertEquals("Produto não encontrado", exception.message)

        verify(exactly = 1) { produtoPort.get(id) }
    }
}
