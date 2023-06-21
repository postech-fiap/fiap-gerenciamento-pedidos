package br.com.fiap.gerenciamentopedidos.api.controllers

import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarProdutoRequest
import br.com.fiap.gerenciamentopedidos.application.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.application.usecases.CadastrarProdutoUseCase
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/produtos")
class ProdutoController(val useCase: CadastrarProdutoUseCase) {
    @PostMapping
    fun post(@RequestBody @Validated request: CadastrarProdutoRequest): ResponseEntity<ProdutoResponse> {
        return ResponseEntity.created(URI.create(""))
            .body(useCase.executar(request))
    }
}
