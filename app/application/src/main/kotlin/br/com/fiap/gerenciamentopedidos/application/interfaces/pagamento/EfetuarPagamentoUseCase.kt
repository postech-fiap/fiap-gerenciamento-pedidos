package br.com.fiap.gerenciamentopedidos.application.interfaces.pagamento

import br.com.fiap.gerenciamentopedidos.domain.models.Pagamento

interface EfetuarPagamentoUseCase {
    fun executar(numeroPedido: String): Pagamento
}