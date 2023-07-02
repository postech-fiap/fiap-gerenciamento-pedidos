package br.com.fiap.gerenciamentopedidos.api.responses

import br.com.fiap.gerenciamentopedidos.domain.dtos.ImagemDto

class ImagemResponse(imagem: ImagemDto) {
    var id: Long? = null
    var caminho: String? = null

    init {
        id = imagem.id
        caminho = imagem.caminho
    }
}