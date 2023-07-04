package br.com.fiap.gerenciamentopedidos.domain.interfaces

import br.com.fiap.gerenciamentopedidos.domain.models.Pagamento

interface PagamentoRepository {
    fun efetuarPagamento(pedido: String): Pagamento
}
