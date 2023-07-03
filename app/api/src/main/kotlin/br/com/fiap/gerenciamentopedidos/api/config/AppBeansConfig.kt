package br.com.fiap.gerenciamentopedidos.api.config

import br.com.fiap.gerenciamentopedidos.domain.ports.drivings.cliente.BuscarClientePorCpf
import br.com.fiap.gerenciamentopedidos.domain.ports.drivings.cliente.CadastrarCliente
import br.com.fiap.gerenciamentopedidos.domain.usecases.cliente.BuscarClientePorCpfImpl
import br.com.fiap.gerenciamentopedidos.domain.usecases.cliente.CadastrarClienteUseCaseImpl
import br.com.fiap.gerenciamentopedidos.domain.usecases.pedido.BuscarPedidosImpl
import br.com.fiap.gerenciamentopedidos.domain.ports.drivens.ClientePort
import br.com.fiap.gerenciamentopedidos.domain.ports.drivens.PedidoPort
import br.com.fiap.gerenciamentopedidos.domain.ports.drivens.ProdutoPort
import br.com.fiap.gerenciamentopedidos.domain.usecases.produto.*
import br.com.fiap.gerenciamentopedidos.infrastructure.adapters.ClienteMySqlAdapter
import br.com.fiap.gerenciamentopedidos.infrastructure.adapters.PedidoMySqlAdapter
import br.com.fiap.gerenciamentopedidos.infrastructure.adapters.ProdutoMySqlAdapter
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.ClienteJpaRepository
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.PedidoJpaRepository
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.ProdutoJpaRepository
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import java.text.SimpleDateFormat


@Configuration
@EnableJpaRepositories(basePackages = ["br.com.fiap.gerenciamentopedidos.infrastructure"])
@EntityScan(basePackages = ["br.com.fiap.gerenciamentopedidos.infrastructure"])
class AppBeansConfig {
    @Bean
    fun objectMapper(): ObjectMapper {
        return JsonMapper.builder()
            .addModule(JavaTimeModule())
            .defaultDateFormat(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"))
            .propertyNamingStrategy(PropertyNamingStrategies.SnakeCaseStrategy.INSTANCE)
            .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
            .build()
    }

    @Bean
    fun produtoMySqlAdapter(repository: ProdutoJpaRepository) = ProdutoMySqlAdapter(repository)

    @Bean
    fun cadastrarProdutoCasoDeUso(produtoPort: ProdutoPort) = CadastrarProdutoImpl(produtoPort)

    @Bean
    fun listarProdutosPorCategoriaUseCase(produtoPort: ProdutoPort) =
        ListarProdutosPorCategoriaImpl(produtoPort)

    @Bean
    fun removerProdutoPorIdUseCase(produtoPort: ProdutoPort) = RemoverProdutoPorIdImpl(produtoPort)

    @Bean
    fun editarProdutoUseCase(produtoPort: ProdutoPort) = EditarProdutoImpl(produtoPort)

    @Bean
    fun alterarDisponibilidadeProdutoUseCase(repository: ProdutoPort) =
        AlterarDisponibilidadeProdutoImpl(repository)

    @Bean
    fun obterProdutoPorIdUseCase(produtoPort: ProdutoPort) = ObterProdutoPorIdImpl(produtoPort)

    @Bean
    fun clienteRepository(clienteJpaRepository: ClienteJpaRepository): ClientePort {
        return ClienteMySqlAdapter(clienteJpaRepository)
    }

    @Bean
    fun cadastrarClienteUseCase(clientePort: ClientePort): CadastrarCliente {
        return CadastrarClienteUseCaseImpl(clientePort)
    }

    @Bean
    fun buscarClientePorCpfUseCase(clientePort: ClientePort): BuscarClientePorCpf {
        return BuscarClientePorCpfImpl(clientePort)
    }

    @Bean
    fun pedidoRepository(pedidoJpaRepository: PedidoJpaRepository): PedidoPort {
        return PedidoMySqlAdapter(pedidoJpaRepository)
    }

    @Bean
    fun buscarPedidosUseCase(pedidoPort: PedidoPort): BuscarPedidosImpl {
        return BuscarPedidosImpl(pedidoPort)
    }

}
