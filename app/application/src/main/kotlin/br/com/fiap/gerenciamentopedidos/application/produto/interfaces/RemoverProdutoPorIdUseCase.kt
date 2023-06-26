package br.com.fiap.gerenciamentopedidos.application.produto.interfaces

interface RemoverProdutoPorIdUseCase {
    fun executar(id: Long)
}
