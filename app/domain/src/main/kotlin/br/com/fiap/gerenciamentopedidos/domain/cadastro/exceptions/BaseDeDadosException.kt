package br.com.fiap.gerenciamentopedidos.domain.cadastro.exceptions

data class BaseDeDadosException(override val message: String): RuntimeException(message)