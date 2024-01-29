package br.com.fiap.gerenciamentopedidos.api.adapters

import br.com.fiap.gerenciamentopedidos.api.requests.CadastrarProdutoRequest
import br.com.fiap.gerenciamentopedidos.api.requests.EditarImagemRequest
import br.com.fiap.gerenciamentopedidos.api.requests.EditarProdutoRequest
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.produto.*
import br.com.fiap.gerenciamentopedidos.domain.models.Imagem
import br.com.fiap.gerenciamentopedidos.domain.models.Produto
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal

@ExtendWith(MockKExtension::class)
class ProdutoAdapterTest {

    @MockK
    lateinit var cadastrarProdutoUseCase: CadastrarProdutoUseCase

    @MockK
    lateinit var editarProdutoUseCase: EditarProdutoUseCase

    @MockK
    lateinit var listarProdutosPorCategoriaUseCase: ListarProdutosPorCategoriaUseCase

    @MockK
    lateinit var obterProdutoPorIdUseCase: ObterProdutoPorIdUseCase

    @MockK
    lateinit var removerProdutoPorIdUseCase: RemoverProdutoPorIdUseCase

    @MockK
    lateinit var alterarDisponibilidadeProdutoUseCase: AlterarDisponibilidadeProdutoUseCase

    @InjectMockKs
    lateinit var adapter: ProdutoAdapterImpl

    @Test
    fun `deve cadastrar um produto com sucesso`() {
        val request = CadastrarProdutoRequest(
            nome = "Produto 1",
            descricao = "descricao",
            categoria = Categoria.BEBIDA,
            valor = BigDecimal(10),
            tempoPreparo = 10,
            imagem = "/caminho.jpg"
        )

        every { cadastrarProdutoUseCase.executar(any()) } returns request.toModel()

        val response = adapter.cadastrarProduto(request)

        assertNotNull(response)
    }

    @Test
    fun `deve editar um produto com sucesso`() {
        val request = EditarProdutoRequest(
            id = 1,
            nome = "Produto 1",
            descricao = "descricao",
            categoria = Categoria.BEBIDA,
            valor = BigDecimal(10),
            tempoPreparo = 10,
            imagem = EditarImagemRequest(1, "/caminho.jpg")
        )

        every { editarProdutoUseCase.executar(any()) } returns request.toModel()

        val response = adapter.editarProduto(request)

        assertNotNull(response)
    }

    @Test
    fun `deve alterar a disponibilidade do produto com sucesso`() {
        every { alterarDisponibilidadeProdutoUseCase.executar(any(), any()) } returns criarProduto()

        val response = adapter.alterarDisponibilidadeProduto(1, true)

        assertNotNull(response)
    }

    @Test
    fun `deve listar produtos por categoria com sucesso`() {
        every { listarProdutosPorCategoriaUseCase.executar(any()) } returns listOf(criarProduto())

        val response = adapter.listarProdutosPorCategoria(Categoria.BEBIDA)

        assertNotNull(response)
        assertFalse(response.isEmpty())
    }

    @Test
    fun `deve obter produto por id com sucesso`() {
        every { obterProdutoPorIdUseCase.executar(any()) } returns criarProduto()

        val response = adapter.obterProdutoPorId(1)

        assertNotNull(response)
    }

    @Test
    fun `deve obter remover por id com sucesso`() {
        every { removerProdutoPorIdUseCase.executar(any()) } returns Unit

        Assertions.assertDoesNotThrow { adapter.removerProdutoPorId(1) }
    }

    private fun criarProduto() = Produto(
        id = 1,
        nome = "Produto 1",
        descricao = "descricao",
        categoria = Categoria.BEBIDA,
        valor = BigDecimal(10),
        tempoPreparo = 10,
        disponivel = true,
        excluido = false,
        imagem = Imagem(1, "/caminho.jpg")
    )
}