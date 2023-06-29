package br.com.fiap.gerenciamentopedidos.domain.valueobjects

data class Email(val endereco: String) {

    init {
        require(endereco.isNotBlank()) { "Email não pode ser vazio" }
        require(endereco.matches(Regex("^[a-zA-Z0-9._]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))) { "Formato do email é inválido!" }
    }
}