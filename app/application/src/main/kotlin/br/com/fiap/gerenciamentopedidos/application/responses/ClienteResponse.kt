package br.com.fiap.gerenciamentopedidos.application.responses

import br.com.fiap.gerenciamentopedidos.domain.cadastro.models.ClienteDomain

data class ClienteResponse(
    val id: Long? = null,
    val cpf: String,
    val nome: String,
    val email: String
) {

    companion object {
        fun fromDomain(clienteDomain: ClienteDomain) =
            ClienteResponse(
                clienteDomain.id,
                clienteDomain.cpf.numero,
                clienteDomain.nome,
                clienteDomain.email.endereco
            )
    }
}
