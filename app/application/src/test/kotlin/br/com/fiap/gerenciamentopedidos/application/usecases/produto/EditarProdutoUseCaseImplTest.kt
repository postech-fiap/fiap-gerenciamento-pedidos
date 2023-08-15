package br.com.fiap.gerenciamentopedidos.application.usecases.produto

import br.com.fiap.gerenciamentopedidos.application.requests.EditarProdutoRequest
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ProdutoRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal

@ExtendWith(MockKExtension::class)
class EditarProdutoUseCaseImplTest {
    @InjectMockKs
    lateinit var useCase: EditarProdutoUseCaseImpl

    @MockK
    lateinit var produtoRepository: ProdutoRepository

    @Test
    fun `deve editar produto com sucesso`() {
        //given
        val id = 1L
        val produtoRequest = EditarProdutoRequest(
            id = id,
            nome = "Nome",
            descricao = null,
            categoria = Categoria.BEBIDA,
            valor = BigDecimal(1),
            tempoPreparo = 1,
            imagem = null
        )

        val produto = produtoRequest.toModel()

        every { produtoRepository.update(produto) } returns produto

        //when
        val result = useCase.executar(produtoRequest)

        //then
        Assertions.assertNotNull(result)
        Assertions.assertEquals(id, result.id)

        verify(exactly = 1) { produtoRepository.update(produto) }
    }
}
