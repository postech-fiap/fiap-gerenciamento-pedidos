package br.com.fiap.gerenciamentopedidos.api.requests

import com.fasterxml.jackson.annotation.JsonProperty

data class AlterarStatusPedidoRequest(
    @JsonProperty("id_pedido")
    val idPedido: Long,
    @JsonProperty("status_pagamento")
    val statusPagamento: String?,
    @JsonProperty("status_pedido")
    val status: String?,
)
