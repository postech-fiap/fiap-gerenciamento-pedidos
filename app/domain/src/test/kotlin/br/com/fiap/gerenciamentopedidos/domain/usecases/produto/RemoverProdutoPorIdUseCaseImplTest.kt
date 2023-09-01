package br.com.fiap.gerenciamentopedidos.domain.usecases.produto

import br.com.fiap.gerenciamentopedidos.domain.interfaces.ProdutoRepository
import br.com.fiap.gerenciamentopedidos.domain.usecases.produto.RemoverProdutoPorIdUseCaseImpl
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
class RemoverProdutoPorIdUseCaseImplTest {
    @InjectMockKs
    lateinit var useCase: RemoverProdutoPorIdUseCaseImpl

    @MockK
    lateinit var produtoRepository: ProdutoRepository

    @Test
    fun `deve exluir produto com sucesso`() {
        //given
        val id = 1L
        every { produtoRepository.remove(id) } returns Unit

        //when
        Assertions.assertDoesNotThrow { useCase.executar(id) }

        //then
        verify(exactly = 1) { produtoRepository.remove(id) }
    }

    @Test
    fun `deve propagar erro ao exluir produto`() {
        //given
        val errorMessage = "Erro ao excluir produto. Detalhes: Error"
        val id = 1L
        every { produtoRepository.remove(id) } throws RuntimeException(errorMessage)

        //when
        val exception = Assertions.assertThrows(RuntimeException::class.java) {
            useCase.executar(id)
        }

        Assertions.assertEquals(errorMessage, exception.message)

        //then
        verify(exactly = 1) { produtoRepository.remove(id) }
    }
}
