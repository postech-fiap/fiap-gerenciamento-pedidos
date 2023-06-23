package br.com.fiap.gerenciamentopedidos.api.controllers

import br.com.fiap.gerenciamentopedidos.application.pedido.usecases.CadastrarPedidoUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pedido")
class PedidoController(val casoDeUso: CadastrarPedidoUseCase) {

    @GetMapping
    fun cadastrarPedido(): String {
        casoDeUso.executar()
        return "Success!"
    }
}
