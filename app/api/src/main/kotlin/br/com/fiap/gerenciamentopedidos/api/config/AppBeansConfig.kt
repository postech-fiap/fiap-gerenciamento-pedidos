package br.com.fiap.gerenciamentopedidos.api.config

import br.com.fiap.gerenciamentopedidos.application.cadastro.interfaces.BuscarClientePorCpfUseCase
import br.com.fiap.gerenciamentopedidos.application.cadastro.interfaces.CadastrarClienteUseCase
import br.com.fiap.gerenciamentopedidos.application.cadastro.usecases.BuscarClientePorCpfUseCaseImpl
import br.com.fiap.gerenciamentopedidos.application.cadastro.usecases.CadastrarClienteUseCaseImpl
import br.com.fiap.gerenciamentopedidos.application.pedido.usecases.BuscarUseCaseImpl
import br.com.fiap.gerenciamentopedidos.application.produto.usecases.*
import br.com.fiap.gerenciamentopedidos.domain.cadastro.interfaces.repositories.ClienteRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.repositories.ProdutoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.repositories.PedidoRepository
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
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.text.SimpleDateFormat


@Configuration
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

    @Bean
    fun pedidoRepository(pedidoJpaRepository: PedidoJpaRepository): PedidoRepository {
        return PedidoMySqlAdapter(pedidoJpaRepository)
    }

    @Bean
    fun buscarPedidosUseCase(pedidoRepository: PedidoRepository): BuscarUseCaseImpl {
        return BuscarUseCaseImpl(pedidoRepository)
    }

}
