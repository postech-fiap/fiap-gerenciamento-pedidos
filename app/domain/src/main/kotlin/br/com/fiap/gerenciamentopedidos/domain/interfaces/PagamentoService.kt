package br.com.fiap.gerenciamentopedidos.domain.interfaces

import br.com.fiap.gerenciamentopedidos.domain.models.Pagamento

interface PagamentoService {
    fun efetuarPagamento(numeroPedido: String): Pagamento
}
