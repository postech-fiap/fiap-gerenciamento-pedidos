package br.com.fiap.gerenciamentopedidos.api.config


import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SpringDocConfig {
    @Bean
    fun api(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("http")
            .pathsToMatch("/**")
            .build()

    }

    @Bean
    fun apiInfo(): OpenAPI {
        return OpenAPI()
            .components(Components())
            .info(Info()
                .title("Gerenciamento de Pedidos")
                .description("Desafio Tech Challange FIAP")
                .version("1.0.0"))
    }
}

