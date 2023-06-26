package br.com.fiap.gerenciamentopedidos.domain.cadastro.models

data class ClienteDomain(
    val id: Long? = null,
    val cpf: Cpf,
    val nome: String,
    val email: Email
) {

    init {
        require(nome.isNotBlank()) { "Nome não pode ser vazio" }
    }
}