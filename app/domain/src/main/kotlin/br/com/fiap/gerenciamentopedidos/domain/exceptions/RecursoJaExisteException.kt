package br.com.fiap.gerenciamentopedidos.domain.exceptions

data class RecursoJaExisteException(override val message: String) : RuntimeException(message)
