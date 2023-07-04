package br.com.fiap.gerenciamentopedidos.application.usecases.produto

import br.com.fiap.gerenciamentopedidos.domain.dtos.ProdutoDto
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.ports.ProdutoPort
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
class ObterProdutoPorIdUseCaseImplTest {
    @InjectMockKs
    lateinit var useCase: ObterProdutoPorIdUseCaseImpl

    @MockK
    lateinit var produtoPort: ProdutoPort

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

        every { produtoPort.get(id) } returns Optional.of(ProdutoDto.fromModel(produto))

        //when
        val result = useCase.executar(id)

        //then
        Assertions.assertNotNull(result)
        Assertions.assertEquals(id, result.id)

        verify(exactly = 1) { produtoPort.get(id) }
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
