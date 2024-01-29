package br.com.fiap.gerenciamentopedidos.domain.dtos

import java.time.OffsetDateTime

data class PedidoDto(
    val id: Long?,
    val number: String?,
    val createdDate: OffsetDateTime,
    var items: List<ItemPedidoDto>
)

data class ItemPedidoDto(
    val name: String?,
    val quantity: Long,
    val comment: String?
)