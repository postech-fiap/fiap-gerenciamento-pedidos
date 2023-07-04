package br.com.fiap.gerenciamentopedidos.domain.dtos

import br.com.fiap.gerenciamentopedidos.domain.models.Cliente
import br.com.fiap.gerenciamentopedidos.domain.valueobjects.Cpf
import br.com.fiap.gerenciamentopedidos.domain.valueobjects.Email

data class ClienteDto(
    val id: Long? = null,
    val cpf: Cpf? = null,
    val nome: String? = null,
    val email: Email? = null
) {
    companion object {
        fun fromModel(cliente: Cliente) = ClienteDto(cliente.id, cliente.cpf, cliente.nome, cliente.email)
    }

    fun toModel() = Cliente(id, cpf!!, nome!!, email!!)
}
