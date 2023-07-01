package br.com.fiap.gerenciamentopedidos.domain.exceptions

data class ValidationException(override val message: String): RuntimeException(message)