package br.com.fiap.gerenciamentopedidos.domain.cadastro.exceptions

data class BusinessException(override val message: String): RuntimeException(message)