package br.com.fiap.gerenciamentopedidos.aplicacao.casosdeuso

import br.com.fiap.gerenciamentopedidos.aplicacao.interfaces.CasoDeUso
import br.com.fiap.gerenciamentopedidos.dominio.interfaces.services.PedidoServico
import br.com.fiap.gerenciamentopedidos.dominio.modelos.Pedido

class CadastrarPedidoCasoDeUso : CasoDeUso {

    val pedidoServico: PedidoServico

    constructor(pedidoServico: PedidoServico) {
        this.pedidoServico = pedidoServico
    }

    fun executar(): Pedido {
        var pedido = Pedido()
        println("Executando caso de uso")
        return pedido
    }
}