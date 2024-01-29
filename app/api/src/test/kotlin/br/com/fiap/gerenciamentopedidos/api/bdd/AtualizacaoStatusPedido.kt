package br.com.fiap.gerenciamentopedidos.api.bdd

import io.cucumber.java.pt.Entao
import io.cucumber.java.pt.Quando
import io.cucumber.java8.Pt
import io.restassured.RestAssured
import io.restassured.response.Response
import org.springframework.http.HttpStatus

private const val ENDPOINT_PEDIDOS = "http://localhost:8080/pedidos/status"

class AtualizacaoStatusPedido : CucumberTest(), Pt {
    lateinit var response: Response

    @Quando("for solicitado a alteração do estatus de um pedido")
    fun CriarUmProduto() {
        response = RestAssured.given()
            .contentType("application/json")
            .body(
                """
                   {
                        "referencia_pedido": "2997433a-f007-4987-88ac-55d9c0141607",
                        "id_pagamento": "12332121",
                        "status_pagamento": "APROVADO"
                    }
                """.trimIndent()
            )
            .`when`()
            .patch(ENDPOINT_PEDIDOS)
    }

    @Entao("deve retornar sucesso na alteração do status do pedido")
    fun ValidarPedidoCriado() {
        response.then()
            .statusCode(HttpStatus.OK.value())
    }
}