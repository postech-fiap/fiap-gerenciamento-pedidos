package br.com.fiap.gerenciamentopedidos.domain.dtos

import br.com.fiap.gerenciamentopedidos.domain.models.Imagem

data class ImagemDto(
    val id: Long? = null,
    val caminho: String? = null
) {
    companion object {
        fun fromModel(imagem: Imagem) = ImagemDto(imagem.id, imagem.caminho)
    }

    fun toModel() = Imagem(id, caminho!!)
}
