package br.com.fiap.gerenciamentopedidos.api.config

import br.com.fiap.gerenciamentopedidos.api.adapters.PedidoAdapterImpl
import br.com.fiap.gerenciamentopedidos.api.adapters.ProdutoAdapterImpl
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ProdutoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.gateways.PagamentoGateway
import br.com.fiap.gerenciamentopedidos.domain.interfaces.gateways.ProducaoGateway
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.AlterarStatusPedidoUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.BuscarPedidosUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.CadastrarPedidoUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.GerarNumeroPedidoUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.produto.*
import br.com.fiap.gerenciamentopedidos.domain.usecases.pedido.AlterarStatusPedidoUseCaseImpl
import br.com.fiap.gerenciamentopedidos.domain.usecases.pedido.BuscarPedidosUseCaseImpl
import br.com.fiap.gerenciamentopedidos.domain.usecases.pedido.CadastrarPedidoUseCaseImpl
import br.com.fiap.gerenciamentopedidos.domain.usecases.pedido.GerarNumeroPedidoUseCaseImpl
import br.com.fiap.gerenciamentopedidos.domain.usecases.produto.*
import br.com.fiap.gerenciamentopedidos.infrastructure.gateways.PagamentoGatewayImpl
import br.com.fiap.gerenciamentopedidos.infrastructure.gateways.ProducaoGatewayImpl
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.PedidoRepositoryImpl
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.ProdutoRepositoryImpl
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.jpa.PedidoJpaRepository
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.jpa.ProdutoJpaRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.web.client.RestTemplate

@Configuration
@EnableJpaRepositories(basePackages = ["br.com.fiap.gerenciamentopedidos.infrastructure"])
@EntityScan(basePackages = ["br.com.fiap.gerenciamentopedidos.infrastructure"])
class AppBeansConfig(
    val restTemplate: RestTemplate
) {
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
    fun pedidoRepository(pedidoJpaRepository: PedidoJpaRepository) = PedidoRepositoryImpl(pedidoJpaRepository)

    @Bean
    fun buscarPedidosUseCase(repository: PedidoRepository) = BuscarPedidosUseCaseImpl(repository)

    @Bean
    fun alterarStatusPedidoUseCase(repository: PedidoRepository, producaoGateway: ProducaoGateway) =
        AlterarStatusPedidoUseCaseImpl(repository, producaoGateway)

    @Bean
    fun gerarNumeroPedidoUseCase(repository: PedidoRepository) = GerarNumeroPedidoUseCaseImpl(repository)

    @Bean
    fun obterProdutosPorIdsUseCase(repository: ProdutoRepository) = ObterProdutosPorIdsUseCaseImpl(repository)

    @Bean
    fun cadastrarPedidoUseCase(
        pedidoRepository: PedidoRepository,
        gerarNumeroPedidoUseCase: GerarNumeroPedidoUseCase,
        obterProdutosPorIdsUseCase: ObterProdutosPorIdsUseCase,
        pagamentoGateway: PagamentoGateway
    ) = CadastrarPedidoUseCaseImpl(
        pedidoRepository,
        gerarNumeroPedidoUseCase,
        obterProdutosPorIdsUseCase,
        pagamentoGateway
    )

    @Bean
    fun pedidoFacade(
        buscarPedidosUseCase: BuscarPedidosUseCase,
        cadastrarPedidoUseCase: CadastrarPedidoUseCase,
        alterarStatusPedidoUseCase: AlterarStatusPedidoUseCase
    ) = PedidoAdapterImpl(buscarPedidosUseCase, cadastrarPedidoUseCase, alterarStatusPedidoUseCase)

    @Bean
    fun produtoFacade(
        cadastrarProdutoUseCase: CadastrarProdutoUseCase,
        editarProdutoUseCase: EditarProdutoUseCase,
        listarProdutosPorCategoriaUseCase: ListarProdutosPorCategoriaUseCase,
        obterProdutoPorIdUseCase: ObterProdutoPorIdUseCase,
        removerProdutoPorIdUseCase: RemoverProdutoPorIdUseCase,
        alterarDisponibilidadeProdutoUseCase: AlterarDisponibilidadeProdutoUseCase
    ) = ProdutoAdapterImpl(
        cadastrarProdutoUseCase,
        editarProdutoUseCase,
        listarProdutosPorCategoriaUseCase,
        obterProdutoPorIdUseCase,
        removerProdutoPorIdUseCase,
        alterarDisponibilidadeProdutoUseCase
    )

    @Bean
    fun producaoGateway(@Value("\${producao.url}") url: String) = ProducaoGatewayImpl(url, restTemplate)

    @Bean
    fun pagametoGateway(@Value("\${pagamento.url}") url: String, objectMapper: ObjectMapper) =
        PagamentoGatewayImpl(url, restTemplate, objectMapper)
}
