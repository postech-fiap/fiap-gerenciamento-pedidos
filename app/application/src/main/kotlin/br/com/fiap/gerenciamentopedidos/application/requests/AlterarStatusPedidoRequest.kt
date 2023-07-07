package br.com.fiap.gerenciamentopedidos.application.requests

import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus

data class AlterarStatusPedidoRequest(
    val pedidoId: Long,
    val status: PedidoStatus,
)