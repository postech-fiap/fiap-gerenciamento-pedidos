package br.com.fiap.gerenciamentopedidos.api.config

import br.com.fiap.gerenciamentopedidos.application.usecases.cliente.BuscarClientePorCpfUseCaseImpl
import br.com.fiap.gerenciamentopedidos.application.usecases.cliente.CadastrarClienteUseCaseImpl
import br.com.fiap.gerenciamentopedidos.application.usecases.pedido.BuscarPedidosUseCaseImpl
import br.com.fiap.gerenciamentopedidos.application.usecases.pedido.CadastrarPedidoUseCaseImpl
import br.com.fiap.gerenciamentopedidos.application.usecases.produto.*
import br.com.fiap.gerenciamentopedidos.domain.interfaces.*
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.ClienteRepositoryImpl
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.PedidoRepositoryImpl
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.ProdutoRepositoryImpl
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.jpa.ClienteJpaRepository
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.jpa.PedidoJpaRepository
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.jpa.ProdutoJpaRepository
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.service.PagamentoServiceImpl
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories


@Configuration
@EnableJpaRepositories(basePackages = ["br.com.fiap.gerenciamentopedidos.infrastructure"])
@EntityScan(basePackages = ["br.com.fiap.gerenciamentopedidos.infrastructure"])
class AppBeansConfig {
    @Bean
    fun produtoMySqlAdapter(repository: ProdutoJpaRepository) = ProdutoRepositoryImpl(repository)

    @Bean
    fun cadastrarProdutoCasoDeUso(repository: ProdutoRepository) = CadastrarProdutoUseCaseImpl(repository)

    @Bean
    fun listarProdutosPorCategoriaUseCase(repository: ProdutoRepository) =
        ListarProdutosPorCategoriaUseCaseImpl(repository)

    @Bean
    fun removerProdutoPorIdUseCase(repository: ProdutoRepository) = RemoverProdutoPorIdUseCaseImpl(repository)

    @Bean
    fun editarProdutoUseCase(repository: ProdutoRepository) = EditarProdutoUseCaseImpl(repository)

    @Bean
    fun alterarDisponibilidadeProdutoUseCase(repository: ProdutoRepository) =
        AlterarDisponibilidadeProdutoUseCaseImpl(repository)

    @Bean
    fun obterProdutoPorIdUseCase(repository: ProdutoRepository) = ObterProdutoPorIdUseCaseImpl(repository)

    @Bean
    fun clienteRepository(clienteJpaRepository: ClienteJpaRepository) = ClienteRepositoryImpl(clienteJpaRepository)

    @Bean
    fun cadastrarClienteUseCase(repository: ClienteRepository) = CadastrarClienteUseCaseImpl(repository)

    @Bean
    fun buscarClientePorCpfUseCase(repository: ClienteRepository) = BuscarClientePorCpfUseCaseImpl(repository)

    @Bean
    fun pedidoRepository(pedidoJpaRepository: PedidoJpaRepository) = PedidoRepositoryImpl(pedidoJpaRepository)

    @Bean
    fun buscarPedidosUseCase(repository: PedidoRepository) = BuscarPedidosUseCaseImpl(repository)

    @Bean
    fun cadastrarPedidoUseCase(cadastrarPedidoRepository: PedidoRepository, clienteRepository: ClienteRepository, pagamentoService: PagamentoService, produtoRepository: ProdutoRepository) = CadastrarPedidoUseCaseImpl(cadastrarPedidoRepository, clienteRepository,pagamentoService, produtoRepository,)

    @Bean
    fun pagamentoService() = PagamentoServiceImpl()

}
