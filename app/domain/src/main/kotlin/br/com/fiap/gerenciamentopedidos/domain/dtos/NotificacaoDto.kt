package br.com.fiap.gerenciamentopedidos.domain.dtos

import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import java.io.Serializable

data class NotificacaoDto(
    val idPedido: Long,
    val idCliente: String,
    val numeroPedido: String,
    val status: PedidoStatus,
) : Serializable