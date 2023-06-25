package br.com.fiap.gerenciamentopedidos.api.config

import br.com.fiap.gerenciamentopedidos.application.pedido.usecases.CadastrarPedidoUseCase
import br.com.fiap.gerenciamentopedidos.application.usecases.CadastrarProdutoUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.repositories.ProdutoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.services.ProdutoService
import br.com.fiap.gerenciamentopedidos.domain.pedido.interfaces.services.PedidoService
import br.com.fiap.gerenciamentopedidos.domain.pedido.services.PedidoServiceImpl
import br.com.fiap.gerenciamentopedidos.domain.services.ProdutoServiceImpl
import br.com.fiap.gerenciamentopedidos.infrastructure.adapters.ProdutoMySqlAdapter
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.ProdutoJpaRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppBeansConfig {
    @Bean
    fun pedidoServico(): PedidoService {
        return PedidoServiceImpl()
    }

    @Bean
    fun cadastrarPedidoCasoDeUso(pedidoService: PedidoService): CadastrarPedidoUseCase {
        return CadastrarPedidoUseCase(pedidoService)
    }

    @Bean
    fun produtoMySqlAdapter(repository: ProdutoJpaRepository): ProdutoRepository {
        return ProdutoMySqlAdapter(repository)
    }

    @Bean
    fun produtoService(repository: ProdutoRepository): ProdutoService {
        return ProdutoServiceImpl(repository)
    }

    @Bean
    fun cadastrarProdutoCasoDeUso(service: ProdutoService): CadastrarProdutoUseCase {
        return CadastrarProdutoUseCase(service)
    }
}
