package br.com.fiap.gerenciamentopedidos.domain.models

import br.com.fiap.gerenciamentopedidos.domain.cadastro.exceptions.ValidationException
import br.com.fiap.gerenciamentopedidos.domain.interfaces.Model
import jakarta.persistence.*

data class Imagem(
    val id: Long? = null,
    val caminho: String? = null
) : Model {
    init {
        validate()
    }

    override fun validate() {
        if (caminho.isNullOrEmpty()) throw ValidationException("Caminho da imagem não informado")
    }
}