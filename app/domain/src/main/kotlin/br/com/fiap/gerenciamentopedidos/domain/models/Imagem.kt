package br.com.fiap.gerenciamentopedidos.domain.models

data class Imagem(
    val id: Long? = null,
    val caminho: String? = null
) {
    init {
        require(caminho.isNullOrEmpty().not()) { "Caminho da imagem não informado" }
        caminho?.matches(Regex("([^\\s]+(\\.(?i)(jpe?g|png|gif|bmp))\$)"))
            ?.let { require(it) { "Formato da imagem é inválido!" } }
    }
}
