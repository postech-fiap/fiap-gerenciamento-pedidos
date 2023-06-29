package br.com.fiap.gerenciamentopedidos.domain.cadastro.exceptions

data class ValidationException(override val message: String): RuntimeException(message)