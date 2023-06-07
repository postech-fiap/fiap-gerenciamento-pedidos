package br.com.fiap.gerenciamentopedidos.api.controladores

import br.com.fiap.gerenciamentopedidos.aplicacao.casosdeuso.CadastrarPedidoCasoDeUso
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pedido")
class PedidoController {

    val casoDeUso: CadastrarPedidoCasoDeUso

    constructor(casoDeUso: CadastrarPedidoCasoDeUso) {
        this.casoDeUso = casoDeUso
    }

    @GetMapping
    fun cadastrarPedido(): String {
        casoDeUso.executar()
        return "Sucesso!"
    }
}
