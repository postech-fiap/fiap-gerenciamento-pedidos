package br.com.fiap.gerenciamentopedidos.domain.dtos

import java.time.OffsetDateTime

data class PedidoDto(
    val id: Long?,
    val number: String?,
    val createDate: OffsetDateTime,
    var items: List<ItemDto>
)
