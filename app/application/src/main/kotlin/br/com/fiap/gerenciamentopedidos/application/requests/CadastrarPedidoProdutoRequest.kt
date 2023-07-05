package br.com.fiap.gerenciamentopedidos.application.requests

import com.fasterxml.jackson.annotation.JsonProperty

data class CadastrarPedidoProdutoRequest(

    @JsonProperty("produto_id")
    val produtoId: Long? = null,
    val quantidade: Int? = null,
    val comentario: String? = null,
)
