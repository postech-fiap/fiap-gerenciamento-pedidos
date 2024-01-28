package br.com.fiap.gerenciamentopedidos.api.bdd

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    plugin = ["pretty"],
    snippets = CucumberOptions.SnippetType.CAMELCASE,
    features = ["app/api/src/test/resources/features"]
)
class RunCucumberTest