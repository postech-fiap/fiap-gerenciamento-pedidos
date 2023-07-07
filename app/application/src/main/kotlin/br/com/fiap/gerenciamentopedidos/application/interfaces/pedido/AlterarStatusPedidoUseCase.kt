package br.com.fiap.gerenciamentopedidos.application.interfaces.pedido

import br.com.fiap.gerenciamentopedidos.application.requests.AlterarStatusPedidoRequest

interface AlterarStatusPedidoUseCase {
    fun executar(alterarStatusPedidoRequest: AlterarStatusPedidoRequest)
}