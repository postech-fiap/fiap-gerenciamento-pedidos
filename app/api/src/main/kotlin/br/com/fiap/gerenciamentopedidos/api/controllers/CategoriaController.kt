package br.com.fiap.gerenciamentopedidos.api.controllers

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/categorias")
class CategoriaController {
    @Operation(summary = "Responsavel por listar as categorias")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Ok",
            content = [ (Content(mediaType = "application/json",
               array = ArraySchema( schema = Schema(implementation = Categoria::class))
            ))])])
    @GetMapping
    fun listarCategorias() = ResponseEntity.ok().body(Categoria.values().toList())
}