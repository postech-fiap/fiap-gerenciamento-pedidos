package br.com.fiap.gerenciamentopedidos.api.requests

import br.com.fiap.gerenciamentopedidos.domain.models.mercadoPago.PagamentoCriado
import br.com.fiap.gerenciamentopedidos.domain.models.mercadoPago.PagamentoCriado.Data

data class PagamentoCriadoRequest(
    val action: String,
    val apiVersion: String,
    val data: DataRequest,
    val dateCreated: String,
    val id: Long,
    val liveMode: Boolean,
    val type: String,
    val userId: String
) {
    class DataRequest(
        val id: String
    ) {

        fun toModel() = Data(
            id = id
        )
    }

    init {
        require("payment.created".equals(this.action, true)) { "Action inválida. Deve ser payment.created" }
        require("payment".equals(this.type, true)) { "Type inválido. Deve ser payment" }
    }

    fun toModel() = PagamentoCriado(
        action = action,
        apiVersion = apiVersion,
        data = data.toModel(),
        dateCreated = dateCreated,
        id = id,
        liveMode = liveMode,
        type = type,
        userId = userId
    )
}