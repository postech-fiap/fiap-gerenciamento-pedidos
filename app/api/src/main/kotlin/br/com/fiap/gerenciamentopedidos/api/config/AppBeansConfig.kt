package br.com.fiap.gerenciamentopedidos.api.config

import br.com.fiap.gerenciamentopedidos.application.cadastro.interfaces.BuscarClientePorCpfUseCase
import br.com.fiap.gerenciamentopedidos.application.cadastro.usecases.BuscarClientePorCpfUseCaseImpl
import br.com.fiap.gerenciamentopedidos.application.pedido.usecases.CadastrarPedidoUseCase
import br.com.fiap.gerenciamentopedidos.domain.cadastro.interfaces.repositories.ClienteRepository
import br.com.fiap.gerenciamentopedidos.domain.pedido.interfaces.services.PedidoService
import br.com.fiap.gerenciamentopedidos.domain.pedido.services.PedidoServiceImpl
import br.com.fiap.gerenciamentopedidos.infrastructure.adapters.ClienteMySqlAdapter
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.ClienteJpaRepository
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(basePackages=["br.com.fiap.gerenciamentopedidos.infrastructure"])
@EntityScan(basePackages=["br.com.fiap.gerenciamentopedidos.infrastructure"])
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
    fun clienteRepository(clienteJpaRepository: ClienteJpaRepository): ClienteRepository {
        return ClienteMySqlAdapter(clienteJpaRepository)
    }

    @Bean
    fun buscarClientePorCpfUseCase(clienteRepository: ClienteRepository): BuscarClientePorCpfUseCase {
        return BuscarClientePorCpfUseCaseImpl(clienteRepository)
    }
}
