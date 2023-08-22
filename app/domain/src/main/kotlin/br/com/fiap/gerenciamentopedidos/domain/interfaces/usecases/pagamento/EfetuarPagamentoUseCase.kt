package br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pagamento

import br.com.fiap.gerenciamentopedidos.domain.models.Pagamento

interface EfetuarPagamentoUseCase {
    fun executar(numeroPedido: String): Pagamento
}