package br.com.fiap.gerenciamentopedidos.api.controllers

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CategoriaController {


    @Operation(summary = "Responsavel por listar as categorias")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Ok",
            content = [ (Content(mediaType = "application/json",
               array = ArraySchema( schema = Schema(implementation = Categoria::class))
            ))])])
    @GetMapping("/categorias")
    fun listarCategorias(): List<Categoria> {
        return Categoria.values().toList()
    }
}