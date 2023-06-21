package br.com.fiap.gerenciamentopedidos.api.config

import br.com.fiap.gerenciamentopedidos.application.usecases.CadastrarPedidoUseCase
import br.com.fiap.gerenciamentopedidos.application.usecases.CadastrarProdutoUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.services.PedidoService
import br.com.fiap.gerenciamentopedidos.domain.interfaces.services.ProdutoService
import br.com.fiap.gerenciamentopedidos.domain.services.PedidoServiceImpl
import br.com.fiap.gerenciamentopedidos.domain.services.ProdutoServiceImpl
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.ProdutoRepositoryImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppBeansConfig {
    @Bean
    fun pedidoService(): PedidoService {
        return PedidoServiceImpl()
    }

    @Bean
    fun cadastrarPedidoUseCase(service: PedidoService): CadastrarPedidoUseCase {
        return CadastrarPedidoUseCase(service)
    }

    @Bean
    fun produtoService(): ProdutoService {
        return ProdutoServiceImpl(ProdutoRepositoryImpl())
    }

    @Bean
    fun cadastrarProdutoUseCase(service: ProdutoService): CadastrarProdutoUseCase {
        return CadastrarProdutoUseCase(service)
    }
}
