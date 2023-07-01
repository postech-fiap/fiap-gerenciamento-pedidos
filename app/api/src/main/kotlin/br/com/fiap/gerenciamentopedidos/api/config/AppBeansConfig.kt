package br.com.fiap.gerenciamentopedidos.api.config

import br.com.fiap.gerenciamentopedidos.application.interfaces.cliente.BuscarClientePorCpfUseCase
import br.com.fiap.gerenciamentopedidos.application.interfaces.cliente.CadastrarClienteUseCase
import br.com.fiap.gerenciamentopedidos.application.usecases.cliente.BuscarClientePorCpfUseCaseImpl
import br.com.fiap.gerenciamentopedidos.application.usecases.cliente.CadastrarClienteUseCaseImpl
import br.com.fiap.gerenciamentopedidos.application.usecases.pedido.BuscarUseCaseImpl
import br.com.fiap.gerenciamentopedidos.application.usecases.produto.*
import br.com.fiap.gerenciamentopedidos.domain.ports.ClientePort
import br.com.fiap.gerenciamentopedidos.domain.ports.PedidoPort
import br.com.fiap.gerenciamentopedidos.domain.ports.ProdutoPort
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
    fun cadastrarProdutoCasoDeUso(produtoPort: ProdutoPort) = CadastrarProdutoUseCaseImpl(produtoPort)

    @Bean
    fun listarProdutosPorCategoriaUseCase(produtoPort: ProdutoPort) =
        ListarProdutosPorCategoriaUseCaseImpl(produtoPort)

    @Bean
    fun removerProdutoPorIdUseCase(produtoPort: ProdutoPort) = RemoverProdutoPorIdUseCaseImpl(produtoPort)

    @Bean
    fun editarProdutoUseCase(produtoPort: ProdutoPort) = EditarProdutoUseCaseImpl(produtoPort)

    @Bean
    fun alterarDisponibilidadeProdutoUseCase(repository: ProdutoPort) =
        AlterarDisponibilidadeProdutoUseCaseImpl(repository)

    @Bean
    fun obterProdutoPorIdUseCase(produtoPort: ProdutoPort) = ObterProdutoPorIdUseCaseImpl(produtoPort)

    @Bean
    fun clienteRepository(clienteJpaRepository: ClienteJpaRepository): ClientePort {
        return ClienteMySqlAdapter(clienteJpaRepository)
    }

    @Bean
    fun cadastrarClienteUseCase(clientePort: ClientePort): CadastrarClienteUseCase {
        return CadastrarClienteUseCaseImpl(clientePort)
    }

    @Bean
    fun buscarClientePorCpfUseCase(clientePort: ClientePort): BuscarClientePorCpfUseCase {
        return BuscarClientePorCpfUseCaseImpl(clientePort)
    }

    @Bean
    fun pedidoRepository(pedidoJpaRepository: PedidoJpaRepository): PedidoPort {
        return PedidoMySqlAdapter(pedidoJpaRepository)
    }

    @Bean
    fun buscarPedidosUseCase(pedidoPort: PedidoPort): BuscarUseCaseImpl {
        return BuscarUseCaseImpl(pedidoPort)
    }

}
