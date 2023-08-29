package br.com.fiap.gerenciamentopedidos.api.config

import br.com.fiap.gerenciamentopedidos.api.facades.ClienteFacadeImpl
import br.com.fiap.gerenciamentopedidos.api.facades.PagamentoFacadeImpl
import br.com.fiap.gerenciamentopedidos.api.facades.PedidoFacadeImpl
import br.com.fiap.gerenciamentopedidos.api.facades.ProdutoFacadeImpl
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ClienteRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ProdutoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.gateways.BuscarPagamentoPorIdGateway
import br.com.fiap.gerenciamentopedidos.domain.interfaces.gateways.GerarQrCodePagamentoGateway
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.cliente.BuscarClientePorCpfUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.cliente.BuscarClientePorIdUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.cliente.CadastrarClienteUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pagamento.FinalizarPagamentoUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pagamento.GerarQrCodePagamentoUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.AlterarStatusPedidoUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.BuscarPedidosUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.CadastrarPedidoUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.GerarNumeroPedidoUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.produto.AlterarDisponibilidadeProdutoUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.produto.CadastrarProdutoUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.produto.EditarProdutoUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.produto.ListarProdutosPorCategoriaUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.produto.ObterProdutoPorIdUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.produto.ObterProdutosPorIdsUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.produto.RemoverProdutoPorIdUseCase
import br.com.fiap.gerenciamentopedidos.domain.usecases.cliente.BuscarClientePorCpfUseCaseImpl
import br.com.fiap.gerenciamentopedidos.domain.usecases.cliente.BuscarClientePorIdUseCaseImpl
import br.com.fiap.gerenciamentopedidos.domain.usecases.cliente.CadastrarClienteUseCaseImpl
import br.com.fiap.gerenciamentopedidos.domain.usecases.pagamento.FinalizarPagamentoUseCaseImpl
import br.com.fiap.gerenciamentopedidos.domain.usecases.pagamento.GerarQrCodePagamentoUseCaseImpl
import br.com.fiap.gerenciamentopedidos.domain.usecases.pedido.AlterarStatusPedidoUseCaseImpl
import br.com.fiap.gerenciamentopedidos.domain.usecases.pedido.BuscarPedidosUseCaseImpl
import br.com.fiap.gerenciamentopedidos.domain.usecases.pedido.CadastrarPedidoUseCaseImpl
import br.com.fiap.gerenciamentopedidos.domain.usecases.pedido.GerarNumeroPedidoUseCaseImpl
import br.com.fiap.gerenciamentopedidos.domain.usecases.produto.AlterarDisponibilidadeProdutoUseCaseImpl
import br.com.fiap.gerenciamentopedidos.domain.usecases.produto.CadastrarProdutoUseCaseImpl
import br.com.fiap.gerenciamentopedidos.domain.usecases.produto.EditarProdutoUseCaseImpl
import br.com.fiap.gerenciamentopedidos.domain.usecases.produto.ListarProdutosPorCategoriaUseCaseImpl
import br.com.fiap.gerenciamentopedidos.domain.usecases.produto.ObterProdutoPorIdUseCaseImpl
import br.com.fiap.gerenciamentopedidos.domain.usecases.produto.ObterProdutosPorIdsUseCaseImpl
import br.com.fiap.gerenciamentopedidos.domain.usecases.produto.RemoverProdutoPorIdUseCaseImpl
import br.com.fiap.gerenciamentopedidos.infrastructure.gateways.BuscarPagamentoPorIdHttpGatewayImpl
import br.com.fiap.gerenciamentopedidos.infrastructure.gateways.GerarQrCodePagamentoHttpGatewayImpl
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.ClienteRepositoryImpl
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.PedidoRepositoryImpl
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.ProdutoRepositoryImpl
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.jpa.ClienteJpaRepository
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.jpa.PedidoJpaRepository
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.jpa.ProdutoJpaRepository
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.web.client.RestTemplate


@Configuration
@EnableJpaRepositories(basePackages = ["br.com.fiap.gerenciamentopedidos.infrastructure"])
@EntityScan(basePackages = ["br.com.fiap.gerenciamentopedidos.infrastructure"])
class AppBeansConfig(
    val mercadoPagoConfig: MercadoPagoConfig,
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
    fun clienteRepository(clienteJpaRepository: ClienteJpaRepository) = ClienteRepositoryImpl(clienteJpaRepository)

    @Bean
    fun cadastrarClienteUseCase(repository: ClienteRepository) = CadastrarClienteUseCaseImpl(repository)

    @Bean
    fun buscarClientePorCpfUseCase(repository: ClienteRepository) = BuscarClientePorCpfUseCaseImpl(repository)

    @Bean
    fun buscarClientePorIdUseCase(repository: ClienteRepository) = BuscarClientePorIdUseCaseImpl(repository)

    @Bean
    fun pedidoRepository(pedidoJpaRepository: PedidoJpaRepository) = PedidoRepositoryImpl(pedidoJpaRepository)

    @Bean
    fun buscarPedidosUseCase(repository: PedidoRepository) = BuscarPedidosUseCaseImpl(repository)

    @Bean
    fun alterarStatusPedidoUseCase(repository: PedidoRepository) = AlterarStatusPedidoUseCaseImpl(repository)

    @Bean
    fun gerarNumeroPedidoUseCase(repository: PedidoRepository) = GerarNumeroPedidoUseCaseImpl(repository)

    @Bean
    fun obterProdutosPorIdsUseCase(repository: ProdutoRepository) = ObterProdutosPorIdsUseCaseImpl(repository)

    @Bean
    fun cadastrarPedidoUseCase(
        pedidoRepository: PedidoRepository,
        buscarClientePorIdUseCase: BuscarClientePorIdUseCase,
        gerarNumeroPedidoUseCase: GerarNumeroPedidoUseCase,
        obterProdutosPorIdsUseCase: ObterProdutosPorIdsUseCase,
        gerarQrCodePagamentoUseCase: GerarQrCodePagamentoUseCase
    ) = CadastrarPedidoUseCaseImpl(
        pedidoRepository,
        buscarClientePorIdUseCase,
        gerarNumeroPedidoUseCase,
        obterProdutosPorIdsUseCase,
        gerarQrCodePagamentoUseCase
    )

    @Bean
    fun gerarQrCodePagamentoGateway() = GerarQrCodePagamentoHttpGatewayImpl(
        restTemplate,
        mercadoPagoConfig.generateQrcodeEndpoint,
        mercadoPagoConfig.token
    )

    @Bean
    fun buscarPagamentoPorIdGateway() = BuscarPagamentoPorIdHttpGatewayImpl(
        restTemplate,
        mercadoPagoConfig.pagamentoEndpoint,
        mercadoPagoConfig.token
    )

    @Bean
    fun gerarQrCodePagamentoUseCase(gerarQrCodePagamentoGateway: GerarQrCodePagamentoGateway) = GerarQrCodePagamentoUseCaseImpl(gerarQrCodePagamentoGateway)

    @Bean
    fun finalizarPagamentoUseCase(buscarPagamentoPorIdGateway: BuscarPagamentoPorIdGateway) = FinalizarPagamentoUseCaseImpl(buscarPagamentoPorIdGateway)

    @Bean
    fun clienteFacade(
        cadastrarClienteUseCase: CadastrarClienteUseCase,
        buscarClientePorCpfUseCase: BuscarClientePorCpfUseCase
    ) = ClienteFacadeImpl(
        cadastrarClienteUseCase,
        buscarClientePorCpfUseCase
    )

    @Bean
    fun pedidoFacade(
        buscarPedidosUseCase: BuscarPedidosUseCase,
        cadastrarPedidoUseCase: CadastrarPedidoUseCase,
        alterarStatusPedidoUseCase: AlterarStatusPedidoUseCase
    ) = PedidoFacadeImpl(
        buscarPedidosUseCase,
        cadastrarPedidoUseCase,
        alterarStatusPedidoUseCase
    )

    @Bean
    fun produtoFacade(
        cadastrarProdutoUseCase: CadastrarProdutoUseCase,
        editarProdutoUseCase: EditarProdutoUseCase,
        listarProdutosPorCategoriaUseCase: ListarProdutosPorCategoriaUseCase,
        obterProdutoPorIdUseCase: ObterProdutoPorIdUseCase,
        removerProdutoPorIdUseCase: RemoverProdutoPorIdUseCase,
        alterarDisponibilidadeProdutoUseCase: AlterarDisponibilidadeProdutoUseCase
    ) = ProdutoFacadeImpl(
        cadastrarProdutoUseCase,
        editarProdutoUseCase,
        listarProdutosPorCategoriaUseCase,
        obterProdutoPorIdUseCase,
        removerProdutoPorIdUseCase,
        alterarDisponibilidadeProdutoUseCase
    )

    @Bean
    fun pagamentoFacade(
        finalizarPagamentoUseCase: FinalizarPagamentoUseCase
    ) = PagamentoFacadeImpl(
        finalizarPagamentoUseCase
    )
}
