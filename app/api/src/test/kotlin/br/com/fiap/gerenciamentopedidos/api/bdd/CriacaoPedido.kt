package br.com.fiap.gerenciamentopedidos.api.bdd

import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import io.cucumber.java.pt.Dado
import io.cucumber.java.pt.Entao
import io.cucumber.java.pt.Quando
import io.cucumber.java8.Pt
import io.restassured.RestAssured
import io.restassured.response.Response
import org.hamcrest.CoreMatchers
import org.springframework.http.HttpStatus

private const val ENDPOINT_PEDIDOS = "http://localhost:8080/pedidos"
private const val ENDPOINT_PRODUTOS = "http://localhost:8080/produtos"

class CriacaoPedido : CucumberTest(), Pt {
    lateinit var response: Response

    @Dado("o produto escolhido")
    fun CriarUmProdutoParaInserirNoPedido() {
        RestAssured.given()
            .contentType("application/json")
            .body(
                """
                    {
                        "nome": "Hamburguer 3",
                        "descricao": "Hamburger de cupim",
                        "categoria": "LANCHE",
                        "valor": 42.99,
                        "tempo_preparo": 20,
                        "imagem": "/hamburger.png"
                    }
                """.trimIndent()
            )
            .`when`()
            .post(ENDPOINT_PRODUTOS)
            .then()
            .statusCode(HttpStatus.CREATED.value())
    }

    @Quando("for solicitado a criação de um pedido")
    fun CriarUmProduto() {
        response = RestAssured.given()
            .contentType("application/json")
            .body(
                """
                   {
                        "produtos": [
                            {
                                "produto_id": 1,
                                "quantidade": 2,
                                "comentario": "Capricha no bacon"
                            }
                        ]
                    }  
                """.trimIndent()
            )
            .`when`()
            .post(ENDPOINT_PEDIDOS)
    }

    @Entao("deve retornar o pedido criado com sucesso")
    fun ValidarPedidoCriado() {
        response.then()
            .statusCode(HttpStatus.CREATED.value())
            .body("status", CoreMatchers.equalTo(PedidoStatus.PENDENTE))
    }

    @Entao("deve retornar erro por não encontrar o produto escolhido")
    fun ValidarPedidoNaoCriadoPorProdutoInexistente() {
        response.then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .body("detail", CoreMatchers.equalTo("Produtos não encontrados ou indisponíveis"))
    }
}