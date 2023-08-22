package br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.produto

interface RemoverProdutoPorIdUseCase {
    fun executar(id: Long)
}
