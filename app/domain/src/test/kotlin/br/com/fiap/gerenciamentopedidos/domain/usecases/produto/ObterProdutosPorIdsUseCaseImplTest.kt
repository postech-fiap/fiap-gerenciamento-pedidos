package br.com.fiap.gerenciamentopedidos.domain.usecases.produto

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ProdutoRepository
import br.com.fiap.gerenciamentopedidos.domain.models.Produto
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
class ObterProdutosPorIdsUseCaseImplTest {
    @InjectMockKs
    lateinit var useCase: ObterProdutosPorIdsUseCaseImpl

    @MockK
    lateinit var produtoRepository: ProdutoRepository

    @Test
    fun `deve obter produtos por ids com sucesso`() {
        //given
        val id = 1L
        val ids = listOf(id)
        val produto = Produto(
            id = id,
            nome = "Nome",
            descricao = null,
            categoria = Categoria.BEBIDA,
            valor = BigDecimal(1),
            tempoPreparo = 1,
            disponivel = true,
            excluido = false,
            imagem = null
        )

        every { produtoRepository.get(ids) } returns listOf(produto)

        //when
        val result = useCase.executar(ids)

        //then
        Assertions.assertNotNull(result)
        Assertions.assertEquals(id, result[0].id)

        verify(exactly = 1) { produtoRepository.get(ids) }
    }

    @Test
    fun `deve lançar exceção por não encontrar produtos`() {
        //given
        val id = 1L
        val ids = listOf(id)
        every { produtoRepository.get(ids) } returns listOf()

        //when
        val exception = Assertions.assertThrows(RecursoNaoEncontradoException::class.java) {
            useCase.executar(ids)
        }

        //then
        Assertions.assertEquals("Produtos não encontrados ou indisponíveis", exception.message)

        verify(exactly = 1) { produtoRepository.get(ids) }
    }
}
