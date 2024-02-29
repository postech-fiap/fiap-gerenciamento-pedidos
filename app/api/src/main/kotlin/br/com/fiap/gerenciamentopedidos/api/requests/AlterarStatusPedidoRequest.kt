package br.com.fiap.gerenciamentopedidos.api.requests

import com.fasterxml.jackson.annotation.JsonProperty

data class AlterarStatusPedidoRequest(
    @JsonProperty("id")
    val id: Long?,
    @JsonProperty("status_pagamento")
    val statusPagamento: String?,
    @JsonProperty("status")
    val status: String?,
)