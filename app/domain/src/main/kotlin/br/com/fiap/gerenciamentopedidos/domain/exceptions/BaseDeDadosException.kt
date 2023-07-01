package br.com.fiap.gerenciamentopedidos.domain.exceptions

data class BaseDeDadosException(override val message: String): RuntimeException(message)