package br.com.fiap.gerenciamentopedidos.application.responses

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria

class ProdutoResponse(
    var nome: String,
    var descricao: String,
    var categoria: Categoria,
    var valor: Double,
    var tempoPreparo: Int,
    var disponivel: Boolean,
    var excluido: Boolean
) {
}