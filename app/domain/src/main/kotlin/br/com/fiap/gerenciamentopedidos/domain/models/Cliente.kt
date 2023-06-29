package br.com.fiap.gerenciamentopedidos.domain.models

import br.com.fiap.gerenciamentopedidos.domain.valueobjects.Cpf
import br.com.fiap.gerenciamentopedidos.domain.valueobjects.Email

data class Cliente(
    val id: Long? = null,
    val cpf: Cpf,
    val nome: String,
    val email: Email
) {

    init {
        require(nome.isNotBlank()) { "Nome não pode ser vazio" }
    }
}