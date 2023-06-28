package br.com.fiap.gerenciamentopedidos.api.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(basePackages = ["br.com.fiap.gerenciamentopedidos.infrastructure"])
@EntityScan(basePackages = ["br.com.fiap.gerenciamentopedidos.infrastructure"])
class DatabaseConfig(
    @Value("\${spring.datasource.url}")
    private val jdbcUrl: String,
    @Value("\${spring.datasource.username}")
    private val username: String,
    @Value("\${spring.datasource.password}")
    private val password: String
) {
    @Bean
    fun dataSource(): DataSource {
        val config = HikariConfig()
        
        config.jdbcUrl = jdbcUrl
        config.username = username
        config.password = password
        config.isAutoCommit = true

        return HikariDataSource(config)
    }
}
