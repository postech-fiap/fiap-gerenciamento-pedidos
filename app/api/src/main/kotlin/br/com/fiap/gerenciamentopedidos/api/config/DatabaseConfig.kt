package br.com.fiap.gerenciamentopedidos.api.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(basePackages=["br.com.fiap.gerenciamentopedidos.infrastructure"])
@EntityScan(basePackages=["br.com.fiap.gerenciamentopedidos.infrastructure"])
class DatabaseConfig {
    @Bean
    fun dataSource(): DataSource {
        val config = HikariConfig()

        config.driverClassName = "com.mysql.cj.jdbc.Driver"
        config.jdbcUrl = "jdbc:mysql://localhost:3306/gerenciamento_pedidos_db"
        config.username = "root"
        config.password = "123456"
        config.isAutoCommit = true

        return HikariDataSource(config)
    }
}
