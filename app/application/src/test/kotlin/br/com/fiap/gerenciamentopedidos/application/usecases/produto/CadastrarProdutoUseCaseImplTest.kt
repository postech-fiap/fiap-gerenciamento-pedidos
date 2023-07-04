package br.com.fiap.gerenciamentopedidos.application.usecases.produto

import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarProdutoRequest
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
import java.util.*

@ExtendWith(MockKExtension::class)
class CadastrarProdutoUseCaseImplTest {
    @InjectMockKs
    lateinit var useCase: CadastrarProdutoUseCaseImpl

    @MockK
    lateinit var produtoPort: ProdutoRepository

    @Test
    fun `deve cadastrar produto com sucesso`() {
        //given
        val produtoRequest = CadastrarProdutoRequest(
            nome = "Nome",
            descricao = null,
            categoria = Categoria.BEBIDA,
            valor = 1.0,
            tempoPreparo = 1,
            imagem = null
        )

        val dto = ProdutoDto.fromModel(produtoRequest.toModel())

        every { produtoPort.create(dto) } returns dto

        //when
        val result = useCase.executar(produtoRequest)

        //then
        Assertions.assertNotNull(result)

        verify(exactly = 1) { produtoPort.create(dto) }
    }
}
