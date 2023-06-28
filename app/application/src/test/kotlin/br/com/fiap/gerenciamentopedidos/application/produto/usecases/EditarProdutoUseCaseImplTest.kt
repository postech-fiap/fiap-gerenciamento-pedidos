package br.com.fiap.gerenciamentopedidos.application.produto.usecases

import br.com.fiap.gerenciamentopedidos.application.requests.EditarProdutoRequest
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.interfaces.repositories.ProdutoRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class EditarProdutoUseCaseImplTest {
    @InjectMockKs
    lateinit var useCase: EditarProdutoUseCaseImpl

    @MockK
    lateinit var repository: ProdutoRepository

    @Test
    fun `deve editar produto com sucesso`() {
        //given
        val id = 1L
        val produtoRequest = EditarProdutoRequest(
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

        every { repository.update(produtoRequest.toDomain()) } returns produtoRequest.toDomain()

        //when
        val result = useCase.executar(produtoRequest)

        //then
        Assertions.assertNotNull(result)
        Assertions.assertEquals(id, result.id)

        verify(exactly = 1) { repository.update(produtoRequest.toDomain()) }
    }
}