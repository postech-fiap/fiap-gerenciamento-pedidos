package br.com.fiap.gerenciamentopedidos.api.config

import br.com.fiap.gerenciamentopedidos.application.usecases.CadastrarPedidoUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.services.PedidoService
import br.com.fiap.gerenciamentopedidos.domain.services.PedidoServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppBeansConfig {
    @Bean
    fun pedidoServico(): PedidoService {
        return PedidoServiceImpl()
    }

    @Bean
    fun cadastrarPedidoCasoDeUso(pedidoService: PedidoService): CadastrarPedidoUseCase {
        return CadastrarPedidoUseCase(pedidoService)
    }
}
