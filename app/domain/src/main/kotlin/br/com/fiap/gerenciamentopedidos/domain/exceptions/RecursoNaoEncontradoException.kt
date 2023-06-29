package br.com.fiap.gerenciamentopedidos.domain.exceptions

data class RecursoNaoEncontradoException(override val message: String) : RuntimeException(message)