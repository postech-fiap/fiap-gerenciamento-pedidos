package br.com.fiap.gerenciamentopedidos.application.requests

import br.com.fiap.gerenciamentopedidos.domain.models.Imagem

data class EditarImagemRequest(
    val id: Long,
    val caminho: String
) {
    fun toDomain() = Imagem(id, caminho)
}