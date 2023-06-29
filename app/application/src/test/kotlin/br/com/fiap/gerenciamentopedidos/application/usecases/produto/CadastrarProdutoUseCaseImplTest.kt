package br.com.fiap.gerenciamentopedidos.application.usecases.produto

import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarProdutoRequest
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
class CadastrarProdutoUseCaseImplTest {
    @InjectMockKs
    lateinit var useCase: CadastrarProdutoUseCaseImpl

    @MockK
    lateinit var adapter: ProdutoAdapter

    @Test
    fun `deve cadastrar produto com sucesso`() {
        //given
        val id = 1L
        val produtoRequest = CadastrarProdutoRequest(
            nome = "Nome",
            descricao = null,
            categoria = Categoria.BEBIDA,
            valor = 1.0,
            tempoPreparo = 1,
            disponivel = true,
            excluido = false,
            imagem = null
        )

        every { adapter.create(produtoRequest.toDomain()) } returns Produto(
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

        //when
        val result = useCase.executar(produtoRequest)

        //then
        Assertions.assertNotNull(result)
        Assertions.assertEquals(id, result.id)

        verify(exactly = 1) { adapter.create(produtoRequest.toDomain()) }
    }
}
