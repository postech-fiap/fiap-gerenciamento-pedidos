package br.com.fiap.gerenciamentopedidos.infrastructure.exceptions

data class BaseDeDadosException(override val message: String): RuntimeException(message)