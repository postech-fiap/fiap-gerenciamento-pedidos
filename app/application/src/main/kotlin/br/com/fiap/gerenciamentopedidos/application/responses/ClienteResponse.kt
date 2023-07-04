package br.com.fiap.gerenciamentopedidos.application.responses

import br.com.fiap.gerenciamentopedidos.domain.dtos.ClienteDto
import br.com.fiap.gerenciamentopedidos.domain.models.Cliente

data class ClienteResponse(
    val id: Long? = null,
    val cpf: String,
    val nome: String,
    val email: String
) {

    companion object {
        fun fromDomain(clienteDomain: ClienteDto) =
            ClienteResponse(
                clienteDomain.id,
                clienteDomain.cpf!!.numero,
                clienteDomain.nome!!,
                clienteDomain.email!!.endereco
            )
    }
}
