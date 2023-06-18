package br.com.fiap.gerenciamentopedidos.application.pedido.usecases

import br.com.fiap.gerenciamentopedidos.application.pedido.interfaces.UseCase
import br.com.fiap.gerenciamentopedidos.domain.pedido.interfaces.services.PedidoService
import br.com.fiap.gerenciamentopedidos.domain.pedido.models.Pedido

class CadastrarPedidoUseCase(val pedidoService: PedidoService) : UseCase {
    fun executar(): Pedido {
        var pedido = Pedido()
        println("Executando caso de uso")
        return pedido
    }
}
