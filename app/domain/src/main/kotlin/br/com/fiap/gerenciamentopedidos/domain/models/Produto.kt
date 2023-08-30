package br.com.fiap.gerenciamentopedidos.domain.models

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.exceptions.BusinessException
import br.com.fiap.gerenciamentopedidos.domain.interfaces.Model
import java.math.BigDecimal

data class Produto(
    val id: Long? = null,
    val nome: String? = null,
    val descricao: String? = null,
    val categoria: Categoria? = null,
    val valor: BigDecimal = BigDecimal.ZERO,
    val tempoPreparo: Long = 0,
    var disponivel: Boolean = true,
    var excluido: Boolean = false,
    val imagem: Imagem? = null,
) : Model {
    fun alterarDisponibilidade(disponivel: Boolean) {
        if (disponivel) {
            this.disponibilizar()
        } else {
            this.indisponibilizar()
        }
    }

    fun disponibilizar() {
        when {
            disponivel -> throw BusinessException("Produto já está disponível")
            else -> this.disponivel = true
        }
    }

    fun indisponibilizar() {
        when {
            !disponivel -> throw BusinessException("Produto já está indisponível")
            else -> this.disponivel = false
        }
    }

    override fun valid(): Produto {
        require(nome.isNullOrEmpty().not()) { "Nome do produto não informado" }
        require(categoria != null) { "Categoria do produto não informada" }
        require(valor > BigDecimal.ZERO) { "Valor do produto deve ser maior que zero" }
        this.imagem?.valid()
        return this
    }
}
