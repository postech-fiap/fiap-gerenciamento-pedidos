package br.com.fiap.gerenciamentopedidos.domain.ports.drivings.produto

interface RemoverProdutoPorIdUseCase {
    fun executar(id: Long)
}
