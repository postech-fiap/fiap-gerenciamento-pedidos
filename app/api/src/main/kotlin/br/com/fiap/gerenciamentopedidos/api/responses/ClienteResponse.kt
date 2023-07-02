package br.com.fiap.gerenciamentopedidos.api.responses

import br.com.fiap.gerenciamentopedidos.domain.dtos.ClienteDto

data class ClienteResponse(
    val id: Long? = null,
    val cpf: String,
    val nome: String,
    val email: String
) {
    companion object {
        fun fromDto(cliente: ClienteDto) =
            ClienteResponse(cliente.id, cliente.cpf!!.numero, cliente.nome!!, cliente.email!!.endereco)
    }
}
