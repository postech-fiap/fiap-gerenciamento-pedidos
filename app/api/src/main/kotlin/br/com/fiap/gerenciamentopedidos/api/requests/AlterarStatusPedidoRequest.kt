package br.com.fiap.gerenciamentopedidos.api.requests

import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class AlterarStatusPedidoRequest(
    @JsonProperty("referencia_pedido")
    val referenciaPedido: String,
    @JsonProperty("id_pagamento")
    val idPagamento: Long,
    @JsonProperty("status_pagamento")
    val statusPagamento: String,
)