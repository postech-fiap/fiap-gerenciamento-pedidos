package br.com.fiap.gerenciamentopedidos.api.config

import br.com.fiap.gerenciamentopedidos.application.cadastro.interfaces.BuscarClientePorCpfUseCase
import br.com.fiap.gerenciamentopedidos.application.cadastro.interfaces.CadastrarClienteUseCase
import br.com.fiap.gerenciamentopedidos.application.cadastro.usecases.BuscarClientePorCpfUseCaseImpl
import br.com.fiap.gerenciamentopedidos.application.cadastro.usecases.CadastrarClienteUseCaseImpl
import br.com.fiap.gerenciamentopedidos.application.pedido.usecases.CadastrarPedidoUseCase
import br.com.fiap.gerenciamentopedidos.application.produto.usecases.*
import br.com.fiap.gerenciamentopedidos.domain.cadastro.interfaces.repositories.ClienteRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.repositories.ProdutoRepository
import br.com.fiap.gerenciamentopedidos.domain.pedido.interfaces.services.PedidoService
import br.com.fiap.gerenciamentopedidos.domain.pedido.services.PedidoServiceImpl
import br.com.fiap.gerenciamentopedidos.infrastructure.adapters.ClienteMySqlAdapter
import br.com.fiap.gerenciamentopedidos.infrastructure.adapters.ProdutoMySqlAdapter
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.ClienteJpaRepository
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.ProdutoJpaRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppBeansConfig {
    @Bean
    fun pedidoServico() = PedidoServiceImpl()

    @Bean
    fun cadastrarPedidoCasoDeUso(pedidoService: PedidoService) = CadastrarPedidoUseCase(pedidoService)

    @Bean
    fun produtoMySqlAdapter(repository: ProdutoJpaRepository) = ProdutoMySqlAdapter(repository)

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
