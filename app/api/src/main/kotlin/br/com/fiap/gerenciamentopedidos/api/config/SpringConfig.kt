package br.com.fiap.gerenciamentopedidos.api.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.kotlinModule
import io.swagger.v3.core.jackson.ModelResolver
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate
import java.text.SimpleDateFormat

@Configuration
class SpringConfig {

    @Bean
    fun objectMapper(): ObjectMapper {
        return JsonMapper.builder()
            .addModule(JavaTimeModule())
            .addModule(kotlinModule())
            .defaultDateFormat(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"))
            .propertyNamingStrategy(PropertyNamingStrategies.SnakeCaseStrategy.INSTANCE)
            .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .build()
    }

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplateBuilder()
            .messageConverters(MappingJackson2HttpMessageConverter(objectMapper()))
            .build()
    }

    @Bean
    fun modelResolver(objectMapper: ObjectMapper): ModelResolver {
        return ModelResolver(objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE));
    }
}
