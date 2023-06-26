package br.com.fiap.gerenciamentopedidos.domain.cadastro.exceptions

data class RecursoNaoEncontradoException(override val message: String) : RuntimeException(message)