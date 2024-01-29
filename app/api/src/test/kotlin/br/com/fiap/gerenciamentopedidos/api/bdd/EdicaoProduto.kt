package br.com.fiap.gerenciamentopedidos.api.bdd

import io.cucumber.java.pt.Entao
import io.cucumber.java.pt.Quando
import io.cucumber.java8.Pt
import io.restassured.RestAssured
import io.restassured.response.Response
import org.hamcrest.CoreMatchers
import org.springframework.http.HttpStatus

private const val ENDPOINT = "http://localhost:8080/produtos"

class EdicaoProduto : CucumberTest(), Pt {
    lateinit var response: Response

    @Quando("for solicitado a edição de um produto")
    fun editarUmProduto() {
        response = RestAssured.given()
            .contentType("application/json")
            .body(
                """
                    {
                        "id": 1,
                        "nome": "Hamburger especial",
                        "descricao": "Feito com Wagyu",
                        "categoria": "LANCHE",
                        "valor": 89.99,
                        "tempo_preparo": 30,
                        "imagem": {
                            "id": 1,
                            "caminho": "/hamburger_premium.png"
                        }
                    }
                """.trimIndent()
            )
            .`when`()
            .put(ENDPOINT)
    }

    @Entao("deve retornar o produto com os dados alterados")
    fun validarProdutoEditadoComSucesso() {
        response.then()
            .statusCode(HttpStatus.OK.value())
            .body("disponivel", CoreMatchers.equalTo(true))
            .body("excluido", CoreMatchers.equalTo(false))
            .body("nome", CoreMatchers.equalTo("Hamburger especial"))
            .body("descricao", CoreMatchers.equalTo("Feito com Wagyu"))
            .body("valor", CoreMatchers.equalTo(89.99F))
    }

    @Quando("for solicitado a edição de um produto sem id informado")
    fun editarUmProdutoSemId() {
        response = RestAssured.given()
            .contentType("application/json")
            .body(
                """
                    {
                        "nome": "Hamburger especial",
                        "descricao": "Feito com Wagyu",
                        "categoria": "LANCHE",
                        "valor": 89.99,
                        "tempo_preparo": 30,
                        "imagem": {
                            "id": 1,
                            "caminho": "/hamburger_premium.png"
                        }
                    }
                """.trimIndent()
            )
            .`when`()
            .put(ENDPOINT)
    }

    @Entao("deve retornar erro de validação de id não informado")
    fun validarErroEdicaoProdutoSemId() {
        response.then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("detail", CoreMatchers.equalTo(""))
    }
}