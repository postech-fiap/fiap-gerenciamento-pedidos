package br.com.fiap.gerenciamentopedidos.api.controllers

import br.com.fiap.gerenciamentopedidos.api.Application
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.junit.jupiter.SpringExtension


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
    }

    @Test
    fun `deve buscar pedidos com sucesso`() {
    }
}