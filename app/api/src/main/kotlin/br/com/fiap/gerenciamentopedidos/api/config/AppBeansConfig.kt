package br.com.fiap.gerenciamentopedidos.api.config

import br.com.fiap.gerenciamentopedidos.api.adapters.PedidoAdapterImpl
import br.com.fiap.gerenciamentopedidos.api.adapters.ProdutoAdapterImpl
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ProdutoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.gateways.NotificacaoGateway
import br.com.fiap.gerenciamentopedidos.domain.interfaces.gateways.PagamentoGateway
import br.com.fiap.gerenciamentopedidos.domain.interfaces.gateways.ProducaoGateway
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.AlterarStatusPedidoUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.CadastrarPedidoUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.GerarNumeroPedidoUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.produto.*
import br.com.fiap.gerenciamentopedidos.domain.usecases.pedido.AlterarStatusPedidoUseCaseImpl
import br.com.fiap.gerenciamentopedidos.domain.usecases.pedido.CadastrarPedidoUseCaseImpl
import br.com.fiap.gerenciamentopedidos.domain.usecases.pedido.GerarNumeroPedidoUseCaseImpl
import br.com.fiap.gerenciamentopedidos.domain.usecases.produto.*
import br.com.fiap.gerenciamentopedidos.infrastructure.gateways.NotificacaoGatewayImpl
import br.com.fiap.gerenciamentopedidos.infrastructure.gateways.PagamentoGatewayImpl
import br.com.fiap.gerenciamentopedidos.infrastructure.gateways.ProducaoGatewayImpl
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.PedidoRepositoryImpl
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.ProdutoRepositoryImpl
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.jpa.PedidoJpaRepository
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.jpa.ProdutoJpaRepository
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(basePackages = ["br.com.fiap.gerenciamentopedidos.infrastructure"])
@EntityScan(basePackages = ["br.com.fiap.gerenciamentopedidos.infrastructure"])
class AppBeansConfig(private val amqpTemplate: RabbitTemplate) {
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
    fun alterarStatusPedidoUseCase(
        repository: PedidoRepository,
        producaoGateway: ProducaoGateway,
        notificacaoGateway: NotificacaoGateway
    ) = AlterarStatusPedidoUseCaseImpl(repository, producaoGateway, notificacaoGateway)

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
        cadastrarPedidoUseCase: CadastrarPedidoUseCase,
        alterarStatusPedidoUseCase: AlterarStatusPedidoUseCase
    ) = PedidoAdapterImpl(cadastrarPedidoUseCase, alterarStatusPedidoUseCase)

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
    fun producaoGateway(pedidoRecebidoQueue: Queue) = ProducaoGatewayImpl(pedidoRecebidoQueue, amqpTemplate)

    @Bean
    fun pagametoGateway(pedidoCriadoQueue: Queue) = PagamentoGatewayImpl(pedidoCriadoQueue, amqpTemplate)

    @Bean
    fun notificacaoGateway(clienteStatusQueue: Queue) = NotificacaoGatewayImpl(clienteStatusQueue, amqpTemplate)

    @Bean
    fun pedidoCriadoQueue(@Value("\${queue.pedido-criado.name}") name: String) = Queue(name, false)

    @Bean
    fun pedidoRecebidoQueue(@Value("\${queue.pedido-recebido.name}") name: String) = Queue(name, false)

    @Bean
    fun pedidoFinalizadoQueue(@Value("\${queue.pedido-finalizado.name}") name: String) = Queue(name, false)

    @Bean
    fun pedidoStatusQueue(@Value("\${queue.pedido-status.name}") name: String) = Queue(name, false)

    @Bean
    fun clienteStatusQueue(@Value("\${queue.cliente-status.name}") name: String) = Queue(name, false)
}