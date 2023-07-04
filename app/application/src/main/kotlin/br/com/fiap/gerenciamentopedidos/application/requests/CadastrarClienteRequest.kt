package br.com.fiap.gerenciamentopedidos.application.requests

import br.com.fiap.gerenciamentopedidos.domain.models.Cliente
import br.com.fiap.gerenciamentopedidos.domain.valueobjects.Cpf
import br.com.fiap.gerenciamentopedidos.domain.valueobjects.Email
import java.time.OffsetDateTime

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
