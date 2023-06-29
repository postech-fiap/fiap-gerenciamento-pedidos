package br.com.fiap.gerenciamentopedidos.application.interfaces.produto

interface RemoverProdutoPorIdUseCase {
    fun executar(id: Long)
}
