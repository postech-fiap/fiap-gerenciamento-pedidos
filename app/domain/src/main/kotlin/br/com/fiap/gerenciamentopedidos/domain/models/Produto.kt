package br.com.fiap.gerenciamentopedidos.domain.models

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.interfaces.Model

class Produto(
    val nome: String?,
    val descricao: String?,
    val categoria: Categoria?,
    val valor: Double,
    val tempoPreparo: Long,
    val disponivel: Boolean,
    val excluido: Boolean,
    val imagem: String?,
) : Model {
    val id: Int? = null

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