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

    private fun apiInfo(): OpenAPI {
        return OpenAPI()
            .info(Info()
                .title("Gerenciamento de Pedidos")
                .description("Desafio Tech Challange FIAP")
                .version("1.0.0"))
    }
}

