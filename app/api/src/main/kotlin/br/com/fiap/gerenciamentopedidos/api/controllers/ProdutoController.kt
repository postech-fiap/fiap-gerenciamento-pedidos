package br.com.fiap.gerenciamentopedidos.api.controllers

import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarProdutoRequest
import br.com.fiap.gerenciamentopedidos.application.requests.EditarProdutoRequest
import br.com.fiap.gerenciamentopedidos.application.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.produto.*
import br.com.fiap.gerenciamentopedidos.infrastructure.exceptions.BaseDeDadosException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
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
    @Operation(summary = "Cadastrar um produto")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201", description = "Created",
                content = [(Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ProdutoResponse::class)
                ))]
            ),
            ApiResponse(
                responseCode = "500", description = "Internal Server Error",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = BaseDeDadosException::class)
                )]
            )]
    )
    @PostMapping
    fun post(@RequestBody request: CadastrarProdutoRequest): ResponseEntity<ProdutoResponse> {
        val produto = cadastrarProdutoUseCase.executar(request)
        return ResponseEntity.created(URI.create("/produtos/${produto.id}")).body(produto)
    }

    @Operation(summary = "Editar um produto")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Ok",
                content = [(Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ProdutoResponse::class)
                ))]
            ),
            ApiResponse(
                responseCode = "500", description = "Internal Server Error",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = BaseDeDadosException::class)
                )]
            )]
    )
    @PutMapping
    fun put(@RequestBody request: EditarProdutoRequest) =
        ResponseEntity.ok(editarProdutoUseCase.executar(request))


    @Operation(summary = "Editar o status de disponibilidade de um produto")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Ok",
                content = [(Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ProdutoResponse::class)
                ))]
            ),
            ApiResponse(
                responseCode = "500", description = "Internal Server Error",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = BaseDeDadosException::class)
                )]
            ),
            ApiResponse(
                responseCode = "404", description = "Produto não encontrado",
                content = [(Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = RecursoNaoEncontradoException::class)
                ))]
            )]
    )
    @PatchMapping("/{id}/disponivel/{disponivel}")
    fun patch(@PathVariable id: Long, @PathVariable disponivel: Boolean) =
        ResponseEntity.ok(alterarDisponibilidadeProdutoUseCase.executar(id, disponivel))

    @Operation(summary = "Buscar produtos filtrando por categoria")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Ok",
                content = [(Content(
                    mediaType = "application/json",
                    array = ArraySchema(schema = Schema(implementation = ProdutoResponse::class))
                ))]
            ),
            ApiResponse(
                responseCode = "500", description = "Internal Server Error",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = BaseDeDadosException::class)
                )]
            )
        ]
    )
    @GetMapping("/categoria/{categoria}")
    fun get(@PathVariable categoria: Categoria) =
        ResponseEntity.ok(listarProdutosPorCategoriaUseCase.executar(categoria))

    @Operation(summary = "Buscar produtos por Id")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Ok",
                content = [(Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ProdutoResponse::class)
                ))]
            ),
            ApiResponse(
                responseCode = "500", description = "Internal Server Error",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = BaseDeDadosException::class)
                )]
            ),
            ApiResponse(
                responseCode = "404", description = "Produto não encontrado",
                content = [(Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = RecursoNaoEncontradoException::class)
                ))]
            )]
    )
    @GetMapping("/{id}")
    fun get(@PathVariable id: Long) =
        ResponseEntity.ok(obterProdutoPorIdUseCase.executar(id))

    @Operation(summary = "Remover um produto por Id")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204", description = "No Content",
                content = [(Content(mediaType = "application/json"))]
            ),
            ApiResponse(
                responseCode = "500", description = "Internal Server Error",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = BaseDeDadosException::class)
                )]
            ),
            ApiResponse(
                responseCode = "404", description = "Produto não encontrado",
                content = [(Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = RecursoNaoEncontradoException::class)
                ))]
            )]
    )
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        removerProdutoPorIdUseCase.executar(id)
        return ResponseEntity.noContent().build()
    }
}
