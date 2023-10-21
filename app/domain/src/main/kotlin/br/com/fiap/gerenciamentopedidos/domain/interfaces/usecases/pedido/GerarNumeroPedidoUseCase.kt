package br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido

fun interface GerarNumeroPedidoUseCase {
    fun executar(): String
}
