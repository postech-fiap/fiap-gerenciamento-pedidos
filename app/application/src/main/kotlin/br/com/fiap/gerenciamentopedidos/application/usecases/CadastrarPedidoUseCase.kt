package br.com.fiap.gerenciamentopedidos.application.usecases

import br.com.fiap.gerenciamentopedidos.application.interfaces.UseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.services.PedidoService
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido

class CadastrarPedidoUseCase(val pedidoService: PedidoService) : UseCase {
    fun executar(): Pedido {
        var pedido = Pedido()
        println("Executando caso de uso")
        return pedido
    }
}
