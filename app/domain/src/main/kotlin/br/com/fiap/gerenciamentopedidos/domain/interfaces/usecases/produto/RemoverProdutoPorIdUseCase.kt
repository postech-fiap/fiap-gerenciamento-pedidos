package br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.produto

fun interface RemoverProdutoPorIdUseCase {
    fun executar(id: Long)
}
