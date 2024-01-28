package br.com.fiap.gerenciamentopedidos.api.bdd.funcionalidades

import br.com.fiap.gerenciamentopedidos.api.Application
import io.cucumber.spring.CucumberContextConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration
@CucumberContextConfiguration
@SpringBootTest(
    properties = ["spring.main.allow-bean-definition-overriding=true"],
    classes = [Application::class],
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
class FuncionalidadeBase