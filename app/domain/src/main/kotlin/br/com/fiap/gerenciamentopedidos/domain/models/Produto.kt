package br.com.fiap.gerenciamentopedidos.domain.models

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.interfaces.Model

class Produto(
    var nome: String?,
    var descricao: String?,
    var categoria: Categoria?,
    var valor: Double,
    var tempoPreparo: Int,
    var disponivel: Boolean,
    var excluido: Boolean
) : Model {
    var id: Int? = null

    init {
        validate()
    }

    override fun validate() {
        if (nome == null || nome!!.isEmpty()) {
            throw Exception("Nome do produto não informado");
        }

        if (descricao == null || descricao!!.isEmpty()) {
            throw Exception("Descrição do produto não informada");
        }

        if (categoria == null) {
            throw Exception("Categoria do produto não informada");
        }
    }
}