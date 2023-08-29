package br.com.fiap.gerenciamentopedidos.domain.dtos

import br.com.fiap.gerenciamentopedidos.domain.models.mercadoPago.DetalhePagamento

data class MercadoPagoResponsePagamentoDto(
    val id: Long,
    val dateCreated: String,
    val dateApproved: String,
    val dateLastUpdated: String,
    val status: String,
    val externalReference: String
) {
    fun toModel() = DetalhePagamento(
        id = id,
        dateCreated = dateCreated,
        dateApproved = dateApproved,
        dateLastUpdated = dateLastUpdated,
        status = status,
        externalReference = externalReference
    )
}