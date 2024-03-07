package br.com.fiap.gerenciamentopedidos.api.requests

import com.fasterxml.jackson.annotation.JsonProperty

data class AlterarStatusPedidoRequest(
    @JsonProperty("id_pedido")
    val idPedido: Long,
    @JsonProperty("id_pagamento")
    val idPagamento: String?,
    @JsonProperty("status_pagamento")
    val statusPagamento: String?,
    @JsonProperty("status_pedido")
    val status: String?,
)
