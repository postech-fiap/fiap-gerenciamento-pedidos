package br.com.fiap.gerenciamentopedidos.api.controllers

import br.com.fiap.gerenciamentopedidos.api.adapters.interfaces.PagamentoAdapter
import br.com.fiap.gerenciamentopedidos.api.requests.PagamentoCriadoRequest
import br.com.fiap.gerenciamentopedidos.api.responses.StatusPagamentoResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/pagamentos")
class PagamentoController(private val pagamentoAdapter: PagamentoAdapter) {

    @PostMapping("/finalizar")
    fun finalizarPagamento(
        @RequestParam("data.id") dataId: String,
        @RequestParam("type") type: String,
        @RequestBody pagamentoCriadoRequest: PagamentoCriadoRequest
    ): StatusPagamentoResponse {
        require("payment".equals(type, true)) { "O type deve ser payment " }
        require(dataId.equals(pagamentoCriadoRequest.data.id, true)) { "O data.id param deve ser o mesmo da request" }

        return pagamentoAdapter.finalizarPagamento(pagamentoCriadoRequest)
    }
}
