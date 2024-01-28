package br.com.fiap.gerenciamentopedidos.api.bdd.funcionalidades

import io.cucumber.java.pt.Entao
import io.cucumber.java.pt.Quando
import io.cucumber.java8.Pt
import io.restassured.RestAssured
import io.restassured.response.Response
import org.hamcrest.CoreMatchers
import org.springframework.http.HttpStatus

private const val ENDPOINT = "http://localhost:8080/produtos"

class CriacaoProduto : FuncionalidadeBase(), Pt {
    lateinit var response: Response

    @Quando("for solicitado a criação de um produto")
    fun CriarUmProduto() {
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
    fun ValidarProdutoCriado() {
        response.then()
            .statusCode(HttpStatus.CREATED.value())
            .body("disponivel", CoreMatchers.equalTo(true))
            .body("excluido", CoreMatchers.equalTo(false))
    }
}