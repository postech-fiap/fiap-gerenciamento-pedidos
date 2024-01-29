package br.com.fiap.gerenciamentopedidos.api.bdd

import io.cucumber.java.pt.Entao
import io.cucumber.java.pt.Quando
import io.cucumber.java8.Pt
import io.restassured.RestAssured
import io.restassured.response.Response
import org.hamcrest.CoreMatchers
import org.springframework.http.HttpStatus

private const val ENDPOINT = "http://localhost:8080/produtos"

class CriacaoProduto : CucumberTest(), Pt {
    lateinit var response: Response

    @Quando("for solicitado a criação de um produto")
    fun criarUmProduto() {
        response = RestAssured.given()
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
            .post(ENDPOINT)
    }

    @Entao("deve retornar o produto disponível e não excluído")
    fun validarProdutoCriado() {
        response.then()
            .statusCode(HttpStatus.CREATED.value())
            .body("disponivel", CoreMatchers.equalTo(true))
            .body("excluido", CoreMatchers.equalTo(false))
    }

    @Quando("for solicitado a criação de um produto sem atributos")
    fun criarUmProdutoSemDados() {
        response = RestAssured.given()
            .contentType("application/json")
            .body(
                """
                    {
                        "nome": null,
                        "descricao": "Hamburger de cupim",
                        "categoria": "LANCHE",
                        "valor": 42.99,
                        "tempo_preparo": 20,
                        "imagem": "/hamburger.png"
                    }
                """.trimIndent()
            )
            .`when`()
            .post(ENDPOINT)
    }

    @Entao("deve retorar erro de validação")
    fun validarPayloadDeCriacaoDeProdutos() {
        response.then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("detail", CoreMatchers.equalTo("Nome do produto não informado"))
    }
}