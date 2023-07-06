package br.com.fiap.gerenciamentopedidos.application.requests

import com.fasterxml.jackson.annotation.JsonProperty

data class CadastrarPedidoProdutoRequest(

    @JsonProperty("produto_id")
    val produtoId: Long,
    val quantidade: Long,
    val comentario: String? = null
)
