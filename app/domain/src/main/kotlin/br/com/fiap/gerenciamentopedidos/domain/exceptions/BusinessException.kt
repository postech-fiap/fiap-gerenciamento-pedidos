package br.com.fiap.gerenciamentopedidos.domain.exceptions

data class BusinessException(override val message: String): RuntimeException(message)