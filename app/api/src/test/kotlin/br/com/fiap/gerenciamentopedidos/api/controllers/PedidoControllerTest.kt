package br.com.fiap.gerenciamentopedidos.api.controllers

import br.com.fiap.gerenciamentopedidos.api.Application
import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import org.json.JSONObject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*


@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [Application::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PedidoControllerTest {

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @Test
    fun `deve cadastrar pedido com sucesso`() {
    }

    @Test
    fun `deve alterar status do pedido com sucesso`() {
        val request = JSONObject()
            .put("referencia_pedido", UUID.randomUUID())
            .put("id_pagamento", 1L)
            .put("status_pagamento", PagamentoStatus.APROVADO)

        val reqHeaders = HttpHeaders()
        reqHeaders.contentType = MediaType.APPLICATION_JSON
        reqHeaders.accept = listOf(MediaType.APPLICATION_JSON)

        val responseEntity = testRestTemplate.exchange(
            "/pedidos/status",
            HttpMethod.PATCH,
            HttpEntity(request.toString(), reqHeaders),
            Any::class.java
        )

        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.statusCode)
    }
}