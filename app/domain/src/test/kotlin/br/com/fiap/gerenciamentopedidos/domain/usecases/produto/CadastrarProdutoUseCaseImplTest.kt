package br.com.fiap.gerenciamentopedidos.domain.usecases.produto

import br.com.fiap.gerenciamentopedidos.domain.dtos.ProdutoDto
import br.com.fiap.gerenciamentopedidos.domain.dtos.requests.CadastrarProdutoRequest
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
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
class CadastrarProdutoUseCaseImplTest {
    @InjectMockKs
    lateinit var useCase: CadastrarProdutoImpl

    @MockK
    lateinit var produtoPort: ProdutoPort

    @Test
    fun `deve cadastrar produto com sucesso`() {
        //given
        val id = 1L
        val produto = CadastrarProdutoRequest("Nome", null, Categoria.BEBIDA, 1.0, 1)
        val dto = ProdutoDto(id, "Nome", null, Categoria.BEBIDA, 1.0, 1, true, false, null)

        every { produtoPort.create(any()) } returns dto

        //when
        val result = useCase.executar(produto)

        //then
        Assertions.assertNotNull(result)
        Assertions.assertEquals(id, result.id)

        verify(exactly = 1) { produtoPort.create(any()) }
    }
}
