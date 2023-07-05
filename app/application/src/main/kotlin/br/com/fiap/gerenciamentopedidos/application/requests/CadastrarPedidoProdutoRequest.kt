package br.com.fiap.gerenciamentopedidos.application.requests

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class CadastrarPedidoProdutoRequest(

    @JsonProperty("produto_id")
    val produtoId: Long? = null,
    val valor: BigDecimal? = null,
    val tempoPreparo: Long? = null,
    val quantidade: Long? = null,
    val comentario: String? = null
)
