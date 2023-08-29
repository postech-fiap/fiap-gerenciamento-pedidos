package br.com.fiap.gerenciamentopedidos.domain.models.mercadoPago

data class DetalhePagamento(
    val id: Long,
    val dateCreated: String,
    val dateApproved: String,
    val dateLastUpdated: String,
    val status: String,
    val externalReference: String
)
