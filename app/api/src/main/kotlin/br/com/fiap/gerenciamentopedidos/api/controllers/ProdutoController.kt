package br.com.fiap.gerenciamentopedidos.api.controllers

import br.com.fiap.gerenciamentopedidos.api.adapters.interfaces.ProdutoAdapter
import br.com.fiap.gerenciamentopedidos.api.requests.CadastrarProdutoRequest
import br.com.fiap.gerenciamentopedidos.api.requests.EditarProdutoRequest
import br.com.fiap.gerenciamentopedidos.api.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/produtos")
class ProdutoController(private val produtoAdapter: ProdutoAdapter) {

    @PostMapping
    fun post(@RequestBody request: CadastrarProdutoRequest): ResponseEntity<ProdutoResponse> {
        val produto = produtoAdapter.cadastrarProduto(request)
        return ResponseEntity.created(URI.create("/produtos/${produto.id}")).body(produto)
    }

    @PutMapping
    fun put(@RequestBody request: EditarProdutoRequest) =
        ResponseEntity.ok(produtoAdapter.editarProduto(request))

    @PatchMapping("/{id}/disponivel/{disponivel}")
    fun patch(@PathVariable id: Long, @PathVariable disponivel: Boolean) =
        ResponseEntity.ok(produtoAdapter.alterarDisponibilidadeProduto(id, disponivel))

    @GetMapping("/categoria/{categoria}")
    fun get(@PathVariable categoria: Categoria) =
        ResponseEntity.ok(produtoAdapter.listarProdutosPorCategoria(categoria))

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long) =
        ResponseEntity.ok(produtoAdapter.obterProdutoPorId(id))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        produtoAdapter.removerProdutoPorId(id)
        return ResponseEntity.noContent().build()
    }
}
