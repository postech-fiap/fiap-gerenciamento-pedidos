package br.com.fiap.gerenciamentopedidos.domain.dtos

data class ItemDto(
    val name: String?,
    val quantity: Long,
    val comment: String?
)