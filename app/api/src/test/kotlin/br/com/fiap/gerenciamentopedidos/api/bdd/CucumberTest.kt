package br.com.fiap.gerenciamentopedidos.api.bdd

import br.com.fiap.gerenciamentopedidos.api.Application
import io.cucumber.spring.CucumberContextConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration


@ContextConfiguration
@CucumberContextConfiguration
@SpringBootTest(
    classes = [Application::class],
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
class CucumberTest