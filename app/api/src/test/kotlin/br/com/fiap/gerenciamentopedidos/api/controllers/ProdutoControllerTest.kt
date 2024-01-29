package br.com.fiap.gerenciamentopedidos.api.controllers

import br.com.fiap.gerenciamentopedidos.api.adapters.interfaces.ProdutoAdapter
import br.com.fiap.gerenciamentopedidos.api.requests.CadastrarProdutoRequest
import br.com.fiap.gerenciamentopedidos.api.requests.EditarImagemRequest
import br.com.fiap.gerenciamentopedidos.api.requests.EditarProdutoRequest
import br.com.fiap.gerenciamentopedidos.api.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.models.Imagem
import br.com.fiap.gerenciamentopedidos.domain.models.Produto
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.math.BigDecimal


class ProdutoControllerTest : IntegrationTest() {

    @MockkBean
    lateinit var adapter: ProdutoAdapter

    @Test
    fun `deve cadastrar produto com sucesso`() {
        val request = CadastrarProdutoRequest(
            nome = "Produto 1",
            descricao = "descricao",
            categoria = Categoria.BEBIDA,
            valor = BigDecimal(10),
            tempoPreparo = 10,
            imagem = "/caminho.jpg"
        )
        val json = objectMapper.writeValueAsString(request)
        val produto = criarProduto()
        every { adapter.cadastrarProduto(any()) } returns ProdutoResponse(produto)

        mockMvc.post("/produtos") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = json
        }
            .andExpect {
                status { isCreated() }
                MockMvcResultMatchers.jsonPath("$.id").value(produto.id)
                MockMvcResultMatchers.jsonPath("$.disponivel").value(true)
                MockMvcResultMatchers.jsonPath("$.excluido").value(false)
            }
    }

    @Test
    fun `deve editar produto com sucesso`() {
        val request = EditarProdutoRequest(
            id = 1,
            nome = "Produto 1",
            descricao = "descricao",
            categoria = Categoria.BEBIDA,
            valor = BigDecimal(10),
            tempoPreparo = 10,
            imagem = EditarImagemRequest(1, "/caminho.jpg")
        )
        val json = objectMapper.writeValueAsString(request)
        val produto = criarProduto()
        every { adapter.editarProduto(any()) } returns ProdutoResponse(produto)

        mockMvc.put("/produtos") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = json
        }
            .andExpect {
                status { isOk() }
                MockMvcResultMatchers.jsonPath("$.id").value(produto.id)
                MockMvcResultMatchers.jsonPath("$.disponivel").value(true)
                MockMvcResultMatchers.jsonPath("$.excluido").value(false)
            }
    }

    @Test
    fun `deve alterar disponibilidade do produto com sucesso`() {
        val produto = criarProduto()
        every { adapter.alterarDisponibilidadeProduto(any(), any()) } returns ProdutoResponse(produto)

        mockMvc.patch("/produtos/1/disponivel/true")
            .andExpect {
                status { isOk() }
                MockMvcResultMatchers.jsonPath("$.id").value(produto.id)
                MockMvcResultMatchers.jsonPath("$.disponivel").value(true)
                MockMvcResultMatchers.jsonPath("$.excluido").value(false)
            }
    }

    @Test
    fun `deve listar produtos por categoria com sucesso`() {
        val produto = criarProduto()
        every { adapter.listarProdutosPorCategoria(any()) } returns listOf(ProdutoResponse(produto))

        mockMvc.get("/produtos/categoria/BEBIDA")
            .andExpect {
                status { isOk() }
                MockMvcResultMatchers.jsonPath("$.length()").value(1)
                MockMvcResultMatchers.jsonPath("$[0].id").value(produto.id)
                MockMvcResultMatchers.jsonPath("$[0].disponivel").value(true)
                MockMvcResultMatchers.jsonPath("$[0].excluido").value(false)
                MockMvcResultMatchers.jsonPath("$[0].categoria").value(Categoria.BEBIDA)
            }
    }

    @Test
    fun `deve obter produto por id com sucesso`() {
        val produto = criarProduto()
        every { adapter.obterProdutoPorId(any()) } returns ProdutoResponse(produto)

        mockMvc.get("/produtos/1")
            .andExpect {
                status { isOk() }
                MockMvcResultMatchers.jsonPath("$.id").value(produto.id)
                MockMvcResultMatchers.jsonPath("$.disponivel").value(true)
                MockMvcResultMatchers.jsonPath("$.excluido").value(false)
            }
    }

    @Test
    fun `deve remover produto por id com sucesso`() {
        every { adapter.removerProdutoPorId(any()) } returns Unit

        mockMvc.delete("/produtos/1")
            .andExpect { status { isNoContent() } }
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