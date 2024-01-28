package br.com.fiap.gerenciamentopedidos.api.bdd.steps

import br.com.fiap.gerenciamentopedidos.api.Application
import br.com.fiap.gerenciamentopedidos.api.requests.CadastrarProdutoRequest
import br.com.fiap.gerenciamentopedidos.api.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import io.cucumber.java.pt.Entao
import io.cucumber.java.pt.Quando
import io.cucumber.java8.Pt
import io.cucumber.spring.CucumberContextConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration
import java.math.BigDecimal

private const val ENDPOINT = "http://localhost:8080/produtos"

@ContextConfiguration
@CucumberContextConfiguration
@SpringBootTest(
    properties = ["spring.main.allow-bean-definition-overriding=true"],
    classes = [Application::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class StepDefinitionProduto : Pt {
    private lateinit var response: ResponseEntity<ProdutoResponse>

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @Quando("for solicitado a criação de um produto")
    fun CriarUmProduto() {
        val request = CadastrarProdutoRequest(
            nome = "Produto 1",
            descricao = "descricao",
            categoria = Categoria.BEBIDA,
            valor = BigDecimal(10),
            tempoPreparo = 10,
            imagem = "/caminho.jpg"
        )
        response = testRestTemplate.postForEntity("/produtos", request, ProdutoResponse::class.java)
    }

    @Entao("deve retornar o produto disponível e não excluído")
    fun ValidarProdutoCriado() {
        assert(response.statusCode.is2xxSuccessful)
        response.body?.let {
            assert(it.disponivel)
            assert(!it.excluido)
        }
    }
}