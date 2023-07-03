package br.com.fiap.gerenciamentopedidos.domain.dtos.requests

import br.com.fiap.gerenciamentopedidos.domain.models.Imagem

data class EditarImagemRequest(
    val id: Long? = null,
    val caminho: String? = null,
) {
    fun toModel() = Imagem(id, caminho!!)
}