package br.com.fiap.gerenciamentopedidos.domain.models

import br.com.fiap.gerenciamentopedidos.domain.interfaces.Model
import br.com.fiap.gerenciamentopedidos.domain.valueobjects.Cpf
import br.com.fiap.gerenciamentopedidos.domain.valueobjects.Email

data class Cliente(
    val id: Long? = null,
    val cpf: Cpf? = null,
    val nome: String? = null,
    val email: Email? = null
) : Model {
    override fun valid(): Cliente {
        require(nome.isNullOrEmpty().not()) { "Nome não pode ser vazio" }
        return this
    }
}