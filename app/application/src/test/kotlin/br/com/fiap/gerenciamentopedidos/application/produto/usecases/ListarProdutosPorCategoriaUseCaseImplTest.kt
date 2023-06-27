package br.com.fiap.gerenciamentopedidos.application.produto.usecases

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
class ListarProdutosPorCategoriaUseCaseImplTest {
    @InjectMockKs
    lateinit var useCase: ListarProdutosPorCategoriaUseCaseImpl

    @MockK
    lateinit var repository: ProdutoRepository

    @Test
    fun `deve lançar exceção por não encontrar produtos`() {
        //given
        val categoria = Categoria.BEBIDA

        every { repository.get(categoria) } returns emptyList()

        //when
        val exception = Assertions.assertThrows(RecursoNaoEncontradoException::class.java) {
            useCase.executar(categoria)
        }

        //then
        Assertions.assertEquals("Nenhum produto encontrado", exception.message)

        verify(exactly = 1) { repository.get(categoria) }
    }

    @Test
    fun `deve listar produtos por categoria com sucesso`() {
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

        every { repository.get(categoria) } returns listOf(produto)

        //when
        val result = useCase.executar(categoria)

        //then
        Assertions.assertNotNull(result)
        Assertions.assertTrue(result.isNotEmpty())
        Assertions.assertEquals(categoria, result.first().categoria)

        verify(exactly = 1) { repository.get(categoria) }
    }
}
