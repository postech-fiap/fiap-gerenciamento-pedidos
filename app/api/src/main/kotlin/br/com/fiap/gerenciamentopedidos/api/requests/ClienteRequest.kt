package br.com.fiap.gerenciamentopedidos.api.requests

import br.com.fiap.gerenciamentopedidos.domain.dtos.ClienteDto
import br.com.fiap.gerenciamentopedidos.domain.valueobjects.Cpf
import br.com.fiap.gerenciamentopedidos.domain.valueobjects.Email

data class ClienteRequest(
    val cpf: String,
    val nome: String,
    val email: String
) {
    fun toDto() = ClienteDto(cpf = Cpf(cpf), nome = nome, email = Email(email))
}
