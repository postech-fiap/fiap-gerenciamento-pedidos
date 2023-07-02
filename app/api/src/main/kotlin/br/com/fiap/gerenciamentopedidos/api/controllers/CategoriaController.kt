package br.com.fiap.gerenciamentopedidos.api.controllers

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CategoriaController {

    @GetMapping("/categorias")
    fun listarCategorias(): List<Categoria> {
        return Categoria.values().toList()
    }
}