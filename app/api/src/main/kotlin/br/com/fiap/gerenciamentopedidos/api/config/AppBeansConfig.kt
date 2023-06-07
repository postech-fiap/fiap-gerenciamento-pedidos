package br.com.fiap.gerenciamentopedidos.api.config

import br.com.fiap.gerenciamentopedidos.aplicacao.casosdeuso.CadastrarPedidoCasoDeUso
import br.com.fiap.gerenciamentopedidos.dominio.interfaces.services.PedidoServico
import br.com.fiap.gerenciamentopedidos.dominio.servicos.PedidoServicoImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppBeansConfig {
    @Bean
    fun pedidoServico(): PedidoServico {
        return PedidoServicoImpl()
    }

    @Bean
    fun cadastrarPedidoCasoDeUso(pedidoServico: PedidoServico): CadastrarPedidoCasoDeUso {
        return CadastrarPedidoCasoDeUso(pedidoServico)
    }
}
