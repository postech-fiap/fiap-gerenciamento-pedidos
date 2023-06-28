package br.com.fiap.gerenciamentopedidos.application.responses

import br.com.fiap.gerenciamentopedidos.domain.models.Imagem

class ImagemResponse(imagem: Imagem) {
    var id: Long? = null
    var caminho: String? = null

    init {
        id = imagem.id
        caminho = imagem.caminho
    }
}