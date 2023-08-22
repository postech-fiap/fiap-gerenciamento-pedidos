package br.com.fiap.gerenciamentopedidos.application.controllers

import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarProdutoRequest
import br.com.fiap.gerenciamentopedidos.application.requests.EditarProdutoRequest
import br.com.fiap.gerenciamentopedidos.application.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.produto.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/produtos")
class ProdutoController(
    private val cadastrarProdutoUseCase: CadastrarProdutoUseCase,
    private val editarProdutoUseCase: EditarProdutoUseCase,
    private val listarProdutosPorCategoriaUseCase: ListarProdutosPorCategoriaUseCase,
    private val obterProdutoPorIdUseCase: ObterProdutoPorIdUseCase,
    private val removerProdutoPorIdUseCase: RemoverProdutoPorIdUseCase,
    private val alterarDisponibilidadeProdutoUseCase: AlterarDisponibilidadeProdutoUseCase
) {
    fun post(@RequestBody request: CadastrarProdutoRequest): ResponseEntity<ProdutoResponse> {
        val produto = cadastrarProdutoUseCase.executar(request)
        return ResponseEntity.created(URI.create("/produtos/${produto.id}")).body(produto)
    }

    fun put(@RequestBody request: EditarProdutoRequest) =
        ResponseEntity.ok(editarProdutoUseCase.executar(request))

    fun patch(@PathVariable id: Long, @PathVariable disponivel: Boolean) =
        ResponseEntity.ok(alterarDisponibilidadeProdutoUseCase.executar(id, disponivel))

    fun get(@PathVariable categoria: Categoria) =
        ResponseEntity.ok(listarProdutosPorCategoriaUseCase.executar(categoria))

    fun get(@PathVariable id: Long) =
        ResponseEntity.ok(obterProdutoPorIdUseCase.executar(id))

    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        removerProdutoPorIdUseCase.executar(id)
        return ResponseEntity.noContent().build()
    }
}
