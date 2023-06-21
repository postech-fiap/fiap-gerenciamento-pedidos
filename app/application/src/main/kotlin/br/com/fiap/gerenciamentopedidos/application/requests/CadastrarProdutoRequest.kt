package br.com.fiap.gerenciamentopedidos.application.requests

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria

class CadastrarProdutoRequest(
    var nome: String,
    var descricao: String,
    var categoria: Categoria,
    var valor: Double,
    var tempoPreparo: Int,
    var disponivel: Boolean,
    var excluido: Boolean
) {
}