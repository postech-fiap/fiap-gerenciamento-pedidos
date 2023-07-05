package br.com.fiap.gerenciamentopedidos.application.interfaces.pedido

import br.com.fiap.gerenciamentopedidos.application.requests.AlterarStatusPedidoRequest
import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoDto

interface AlterarStatusPedidoUseCase {
    fun executar(alterarStatusPedidoRequest: AlterarStatusPedidoRequest): PedidoDto
}