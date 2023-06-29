package br.com.fiap.gerenciamentopedidos.api.controllers

import br.com.fiap.gerenciamentopedidos.domain.pedido.models.Categorias
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CategoriaController {

    @GetMapping("/categorias")
    fun listarCategorias(): List<Categorias> {
        return Categorias.values().toList()
    }
}