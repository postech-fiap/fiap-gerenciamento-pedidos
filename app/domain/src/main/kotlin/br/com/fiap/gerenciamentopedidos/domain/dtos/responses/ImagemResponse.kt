package br.com.fiap.gerenciamentopedidos.domain.dtos.responses

import br.com.fiap.gerenciamentopedidos.domain.models.Imagem

class ImagemResponse(imagem: Imagem) {
    val id: Long?
    val caminho: String?

    init {
        id = imagem.id
        caminho = imagem.caminho
    }
}