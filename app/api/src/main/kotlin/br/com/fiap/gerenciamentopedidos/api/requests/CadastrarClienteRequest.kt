package br.com.fiap.gerenciamentopedidos.api.requests

import br.com.fiap.gerenciamentopedidos.domain.models.Cliente
import br.com.fiap.gerenciamentopedidos.domain.valueobjects.Cpf
import br.com.fiap.gerenciamentopedidos.domain.valueobjects.Email

data class CadastrarClienteRequest(
    val cpf: String,
    val nome: String,
    val email: String
) {
    init {
        require(nome.isNotBlank()) { "Nome não pode ser vazio" }
        require(cpf.isNotBlank()) { "Cpf não pode ser vazio" }
    }

    fun toModel() = Cliente(cpf = Cpf(cpf), nome = nome, email = Email(email))
}