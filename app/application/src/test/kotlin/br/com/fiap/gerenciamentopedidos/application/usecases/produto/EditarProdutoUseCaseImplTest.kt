package br.com.fiap.gerenciamentopedidos.application.usecases.produto

import br.com.fiap.gerenciamentopedidos.application.requests.EditarProdutoRequest
import br.com.fiap.gerenciamentopedidos.domain.dtos.ProdutoDto
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

@ExtendWith(MockKExtension::class)
class EditarProdutoUseCaseImplTest {
    @InjectMockKs
    lateinit var useCase: EditarProdutoUseCaseImpl

    @MockK
    lateinit var produtoPort: ProdutoRepository

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
            imagem = null
        )

        val dto = ProdutoDto.fromModel(produtoRequest.toDomain())

        every { produtoPort.update(dto) } returns dto

        //when
        val result = useCase.executar(produtoRequest)

        //then
        Assertions.assertNotNull(result)
        Assertions.assertEquals(id, result.id)

        verify(exactly = 1) { produtoPort.update(dto) }
    }
}
