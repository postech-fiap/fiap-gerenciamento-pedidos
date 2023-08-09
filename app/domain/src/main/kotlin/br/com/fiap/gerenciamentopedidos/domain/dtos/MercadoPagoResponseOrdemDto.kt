package br.com.fiap.gerenciamentopedidos.domain.dtos

data class MercadoPagoResponseOrdemDto(
    val inStoreOrderId: String,
    val qrData: String
)