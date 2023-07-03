package br.com.fiap.gerenciamentopedidos.domain.dtos.requests

import br.com.fiap.gerenciamentopedidos.domain.models.Cliente
import br.com.fiap.gerenciamentopedidos.domain.valueobjects.Cpf
import br.com.fiap.gerenciamentopedidos.domain.valueobjects.Email

data class CadastrarClienteRequest(
    val cpf: String,
    val nome: String,
    val email: String
) {
    fun toModel() = Cliente(cpf = Cpf(cpf), nome = nome, email = Email(email))
}
