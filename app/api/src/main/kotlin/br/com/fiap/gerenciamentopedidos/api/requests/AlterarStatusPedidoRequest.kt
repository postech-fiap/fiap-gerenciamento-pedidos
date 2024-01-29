package br.com.fiap.gerenciamentopedidos.api.requests

import com.fasterxml.jackson.annotation.JsonProperty

data class AlterarStatusPedidoRequest(
    @JsonProperty("referencia_pedido")
    val referenciaPedido: String,
    @JsonProperty("id_pagamento")
    val idPagamento: String,
    @JsonProperty("status_pagamento")
    val statusPagamento: String,
)