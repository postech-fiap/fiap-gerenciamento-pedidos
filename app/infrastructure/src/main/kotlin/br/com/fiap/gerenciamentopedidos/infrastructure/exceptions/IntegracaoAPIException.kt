package br.com.fiap.gerenciamentopedidos.infrastructure.exceptions

data class IntegracaoAPIException(override val message: String): RuntimeException(message)