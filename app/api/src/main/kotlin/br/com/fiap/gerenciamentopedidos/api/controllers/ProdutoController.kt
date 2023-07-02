package br.com.fiap.gerenciamentopedidos.api.controllers

import br.com.fiap.gerenciamentopedidos.api.requests.CadastrarProdutoRequest
import br.com.fiap.gerenciamentopedidos.api.requests.EditarProdutoRequest
import br.com.fiap.gerenciamentopedidos.api.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.ports.drivings.produto.*
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/produtos")
class ProdutoController(
    private val cadastrarProdutoUseCase: CadastrarProdutoPort,
    private val editarProdutoUseCase: EditarProdutoUseCase,
    private val listarProdutosPorCategoriaUseCase: ListarProdutosPorCategoriaUseCase,
    private val obterProdutoPorIdUseCase: ObterProdutoPorIdUseCase,
    private val removerProdutoPorIdUseCase: RemoverProdutoPorIdUseCase,
    private val alterarDisponibilidadeProdutoUseCase: AlterarDisponibilidadeProdutoPort
) {
    @PostMapping
    fun post(@RequestBody @Validated request: CadastrarProdutoRequest): ResponseEntity<ProdutoResponse> {
        val produto = cadastrarProdutoUseCase.executar(request.toDto())
        return ResponseEntity.created(URI.create("/produtos/${produto.id}")).body(ProdutoResponse(produto))
    }

    @PutMapping
    fun put(@RequestBody request: EditarProdutoRequest) =
        ResponseEntity.ok(editarProdutoUseCase.executar(request.toDto()))

    @PatchMapping("/{id}/disponivel/{disponivel}")
    fun patch(@PathVariable id: Long, @PathVariable disponivel: Boolean) =
        ResponseEntity.ok(alterarDisponibilidadeProdutoUseCase.executar(id, disponivel))

    @GetMapping("/categoria/{categoria}")
    fun get(@PathVariable categoria: Categoria) =
        ResponseEntity.ok(listarProdutosPorCategoriaUseCase.executar(categoria))

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long) =
        ResponseEntity.ok(obterProdutoPorIdUseCase.executar(id))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        removerProdutoPorIdUseCase.executar(id)
        return ResponseEntity.noContent().build()
    }
}
