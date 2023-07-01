package br.com.fiap.gerenciamentopedidos.application.requests

import br.com.fiap.gerenciamentopedidos.domain.models.Cliente
import br.com.fiap.gerenciamentopedidos.domain.valueobjects.Cpf
import br.com.fiap.gerenciamentopedidos.domain.valueobjects.Email

data class ClienteRequest(
    val cpf: String,
    val nome: String,
    val email: String
) {

    fun toDomain() =
        Cliente(
            cpf = Cpf(cpf),
            nome = nome,
            email = Email(email)
        )
}
