package br.com.fiap.gerenciamentopedidos.api.config

import br.com.fiap.gerenciamentopedidos.application.cadastro.interfaces.BuscarClientePorCpfUseCase
import br.com.fiap.gerenciamentopedidos.application.cadastro.interfaces.CadastrarClienteUseCase
import br.com.fiap.gerenciamentopedidos.application.cadastro.usecases.BuscarClientePorCpfUseCaseImpl
import br.com.fiap.gerenciamentopedidos.application.cadastro.usecases.CadastrarClienteUseCaseImpl
import br.com.fiap.gerenciamentopedidos.application.pedido.usecases.CadastrarPedidoUseCase
import br.com.fiap.gerenciamentopedidos.domain.cadastro.interfaces.repositories.ClienteRepository
import br.com.fiap.gerenciamentopedidos.application.usecases.*
import br.com.fiap.gerenciamentopedidos.domain.interfaces.repositories.ProdutoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.services.ProdutoService
import br.com.fiap.gerenciamentopedidos.domain.pedido.interfaces.services.PedidoService
import br.com.fiap.gerenciamentopedidos.domain.pedido.services.PedidoServiceImpl
import br.com.fiap.gerenciamentopedidos.infrastructure.adapters.ClienteMySqlAdapter
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.ClienteJpaRepository
import org.springframework.boot.autoconfigure.domain.EntityScan
import br.com.fiap.gerenciamentopedidos.domain.services.ProdutoServiceImpl
import br.com.fiap.gerenciamentopedidos.infrastructure.adapters.ProdutoMySqlAdapter
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.ProdutoJpaRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
class AppBeansConfig {

    @Bean
    fun pedidoServico() = PedidoServiceImpl()

    @Bean
    fun cadastrarPedidoCasoDeUso(pedidoService: PedidoService) = CadastrarPedidoUseCase(pedidoService)

    @Bean
    fun produtoMySqlAdapter(repository: ProdutoJpaRepository) = ProdutoMySqlAdapter(repository)

    @Bean
    fun produtoService(repository: ProdutoRepository) = ProdutoServiceImpl(repository)

    @Bean
    fun cadastrarProdutoCasoDeUso(service: ProdutoService) = CadastrarProdutoUseCase(service)

    @Bean
    fun listarProdutosPorCategoriaUseCase(service: ProdutoService) = ListarProdutosPorCategoriaUseCase(service)

    @Bean
    fun removerProdutoPorIdUseCase(service: ProdutoService) = RemoverProdutoPorIdUseCase(service)

    @Bean
    fun editarProdutoUseCase(service: ProdutoService) = EditarProdutoUseCase(service)

    @Bean
    fun alterarDisponibilidadeProdutoUseCase(service: ProdutoService) = AlterarDisponibilidadeProdutoUseCase(service)

    @Bean
    fun obterProdutoPorIdUseCase(service: ProdutoService) = ObterProdutoPorIdUseCase(service)
    fun cadastrarPedidoCasoDeUso(pedidoService: PedidoService): CadastrarPedidoUseCase {
        return CadastrarPedidoUseCase(pedidoService)
    }

    @Bean
    fun clienteRepository(clienteJpaRepository: ClienteJpaRepository): ClienteRepository {
        return ClienteMySqlAdapter(clienteJpaRepository)
    }

    @Bean
    fun cadastrarClienteUseCase(clienteRepository: ClienteRepository): CadastrarClienteUseCase {
        return CadastrarClienteUseCaseImpl(clienteRepository)
    }

    @Bean
    fun buscarClientePorCpfUseCase(clienteRepository: ClienteRepository): BuscarClientePorCpfUseCase {
        return BuscarClientePorCpfUseCaseImpl(clienteRepository)
    }
}
