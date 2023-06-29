package br.com.fiap.gerenciamentopedidos.application.usecases.produto

import br.com.fiap.gerenciamentopedidos.domain.exceptions.BusinessException
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.adapters.ProdutoAdapter
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
class AlterarDisponibilidadeProdutoUseCaseImplTest {

    @InjectMockKs
    lateinit var useCase: AlterarDisponibilidadeProdutoUseCaseImpl

    @MockK
    lateinit var adapter: ProdutoAdapter

    @Test
    fun `deve alterar disponibilidade do produto com sucesso`() {
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

        every { adapter.get(id) } returns Optional.of(produto)
        every { adapter.update(produto) } returns produto

        //when
        val result = useCase.executar(id, false)

        //then
        Assertions.assertEquals(produto.id, result.id)
        Assertions.assertEquals(false, result.disponivel)

        verify(exactly = 1) { adapter.get(id) }
        verify(exactly = 1) { adapter.update(produto) }
    }

    @Test
    fun `deve lançar exceção ao alterar disponibilidade de produto`() {
        //given
        val id = 1L
        val produto = Produto(
            id = id,
            nome = "Nome",
            descricao = null,
            categoria = Categoria.BEBIDA,
            valor = 1.0,
            tempoPreparo = 1,
            disponivel = false,
            excluido = false,
            imagem = null
        )

        every { adapter.get(id) } returns Optional.of(produto)

        //when
        val exception = Assertions.assertThrows(BusinessException::class.java) {
            useCase.executar(id, false)
        }

        //then
        Assertions.assertEquals("Produto já está indisponível", exception.message)

        verify(exactly = 1) { adapter.get(id) }
        verify(exactly = 0) { adapter.update(produto) }
    }

    @Test
    fun `deve lançar exceção por não encontrar produto`() {
        //given
        val id = 1L

        every { adapter.get(id) } returns Optional.empty()

        //when
        val exception = Assertions.assertThrows(RecursoNaoEncontradoException::class.java) {
            useCase.executar(id, false)
        }

        //then
        Assertions.assertEquals("Produto não encontrado", exception.message)

        verify(exactly = 1) { adapter.get(id) }
    }
}
