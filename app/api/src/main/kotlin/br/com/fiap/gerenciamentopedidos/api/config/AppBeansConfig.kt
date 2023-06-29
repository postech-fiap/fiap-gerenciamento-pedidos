package br.com.fiap.gerenciamentopedidos.api.config

import br.com.fiap.gerenciamentopedidos.application.interfaces.cliente.BuscarClientePorCpfUseCase
import br.com.fiap.gerenciamentopedidos.application.interfaces.cliente.CadastrarClienteUseCase
import br.com.fiap.gerenciamentopedidos.application.usecases.cliente.BuscarClientePorCpfUseCaseImpl
import br.com.fiap.gerenciamentopedidos.application.usecases.cliente.CadastrarClienteUseCaseImpl
import br.com.fiap.gerenciamentopedidos.application.usecases.pedido.BuscarUseCaseImpl
import br.com.fiap.gerenciamentopedidos.application.usecases.produto.*
import br.com.fiap.gerenciamentopedidos.domain.adapters.ClienteAdapter
import br.com.fiap.gerenciamentopedidos.domain.adapters.PedidoAdapter
import br.com.fiap.gerenciamentopedidos.domain.adapters.ProdutoAdapter
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
    fun cadastrarProdutoCasoDeUso(adapter: ProdutoAdapter) = CadastrarProdutoUseCaseImpl(adapter)

    @Bean
    fun listarProdutosPorCategoriaUseCase(adapter: ProdutoAdapter) =
        ListarProdutosPorCategoriaUseCaseImpl(adapter)

    @Bean
    fun removerProdutoPorIdUseCase(adapter: ProdutoAdapter) = RemoverProdutoPorIdUseCaseImpl(adapter)

    @Bean
    fun editarProdutoUseCase(adapter: ProdutoAdapter) = EditarProdutoUseCaseImpl(adapter)

    @Bean
    fun alterarDisponibilidadeProdutoUseCase(repository: ProdutoAdapter) =
        AlterarDisponibilidadeProdutoUseCaseImpl(repository)

    @Bean
    fun obterProdutoPorIdUseCase(adapter: ProdutoAdapter) = ObterProdutoPorIdUseCaseImpl(adapter)

    @Bean
    fun clienteRepository(clienteJpaRepository: ClienteJpaRepository): ClienteAdapter {
        return ClienteMySqlAdapter(clienteJpaRepository)
    }

    @Bean
    fun cadastrarClienteUseCase(clienteAdapter: ClienteAdapter): CadastrarClienteUseCase {
        return CadastrarClienteUseCaseImpl(clienteAdapter)
    }

    @Bean
    fun buscarClientePorCpfUseCase(clienteAdapter: ClienteAdapter): BuscarClientePorCpfUseCase {
        return BuscarClientePorCpfUseCaseImpl(clienteAdapter)
    }

    @Bean
    fun pedidoRepository(pedidoJpaRepository: PedidoJpaRepository): PedidoAdapter {
        return PedidoMySqlAdapter(pedidoJpaRepository)
    }

    @Bean
    fun buscarPedidosUseCase(pedidoAdapter: PedidoAdapter): BuscarUseCaseImpl {
        return BuscarUseCaseImpl(pedidoAdapter)
    }

}
