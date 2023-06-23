package br.com.fiap.gerenciamentopedidos.domain.cadastro.exceptions

data class RecursoJaExisteException(override val message: String) : RuntimeException(message)
