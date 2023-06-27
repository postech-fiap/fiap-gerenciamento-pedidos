package br.com.fiap.gerenciamentopedidos.application.requests

import br.com.fiap.gerenciamentopedidos.domain.cadastro.models.ClienteDomain
import br.com.fiap.gerenciamentopedidos.domain.cadastro.models.Cpf
import br.com.fiap.gerenciamentopedidos.domain.cadastro.models.Email

data class ClienteRequest(
    val cpf: String,
    val nome: String,
    val email: String
) {

    fun toDomain() =
        ClienteDomain(
            cpf = Cpf(cpf),
            nome = nome,
            email = Email(email)
        )
}
