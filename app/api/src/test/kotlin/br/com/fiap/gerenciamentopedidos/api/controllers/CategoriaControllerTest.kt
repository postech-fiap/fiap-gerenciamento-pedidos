package br.com.fiap.gerenciamentopedidos.api.controllers

import br.com.fiap.gerenciamentopedidos.api.Application
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [Application::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoriaControllerTest {

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @Test
    fun `deve retornar categorias com sucesso`() {
        val responseEntity = testRestTemplate.exchange("/categorias", HttpMethod.GET, null, List::class.java)

        Assertions.assertEquals(HttpStatus.OK, responseEntity.statusCode)
        Assertions.assertFalse(responseEntity.body!!.isEmpty())
        Assertions.assertEquals(Categoria.LANCHE.name, responseEntity.body!![0])
        Assertions.assertEquals(Categoria.ACOMPANHAMENTO.name, responseEntity.body!![1])
        Assertions.assertEquals(Categoria.BEBIDA.name, responseEntity.body!![2])
        Assertions.assertEquals(Categoria.SOBREMESA.name, responseEntity.body!![3])
    }
}