package br.com.fiap.gerenciamentopedidos.infrastructure.repositories.service

import br.com.fiap.gerenciamentopedidos.domain.dtos.PagamentoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PagamentoService
import java.math.BigDecimal
import java.time.OffsetDateTime

class PagamentoServiceImpl : PagamentoService {

    override fun efetuarPagamento(numeroPedido: String, valor: BigDecimal): PagamentoDto {
        return PagamentoDto(
            dataHora = OffsetDateTime.now(),
            status = PagamentoStatus.APROVADO,
        )
    }
}
