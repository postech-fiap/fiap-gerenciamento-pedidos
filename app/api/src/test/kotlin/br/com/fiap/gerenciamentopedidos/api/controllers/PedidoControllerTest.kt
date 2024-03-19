package br.com.fiap.gerenciamentopedidos.api.controllers

import br.com.fiap.gerenciamentopedidos.api.adapters.interfaces.PedidoAdapter
import br.com.fiap.gerenciamentopedidos.api.requests.CadastrarPedidoProdutoRequest
import br.com.fiap.gerenciamentopedidos.api.requests.CadastrarPedidoRequest
import br.com.fiap.gerenciamentopedidos.api.responses.PedidoResponse
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.models.Imagem
import br.com.fiap.gerenciamentopedidos.domain.models.Item
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import br.com.fiap.gerenciamentopedidos.domain.models.Produto
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import java.math.BigDecimal


class PedidoControllerTest : IntegrationTest() {

    @MockkBean
    lateinit var adapter: PedidoAdapter

    @Test
    fun `deve cadastrar pedido com sucesso`() {
        val request = CadastrarPedidoRequest("1", listOf(CadastrarPedidoProdutoRequest(1, 1, "Sem mostarda")))
        val json = objectMapper.writeValueAsString(request)
        val pedido = criarPedido()
        every { adapter.cadastrarPedido(any()) } returns PedidoResponse(pedido)

        mockMvc.post("/pedidos") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = json
        }
            .andExpect {
                status { isCreated() }
                jsonPath("$.id").value(pedido.id)
                jsonPath("$.status").value(PedidoStatus.PENDENTE)
            }
    }

    private fun criarPedido() = Pedido(
        numero = "1", clienteId = "1", items = listOf(
            Item(
                id = 1,
                quantidade = 1,
                comentario = "Sem mostarda",
                valorPago = BigDecimal(10),
                produto = Produto(
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
            )
        )
    )
}