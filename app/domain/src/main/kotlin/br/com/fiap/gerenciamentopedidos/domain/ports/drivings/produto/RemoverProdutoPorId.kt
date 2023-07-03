package br.com.fiap.gerenciamentopedidos.domain.ports.drivings.produto

interface RemoverProdutoPorId {
    fun executar(id: Long)
}
