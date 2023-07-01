package br.com.fiap.gerenciamentopedidos.domain.valueobjects

data class Cpf(val numero: String) {

    init {
        require(numero.isNotBlank()) { "CPF não pode ser vazio" }
        require(numero.matches(Regex("(\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2})|\\d{11}"))) { "Formato do CPF é inválido!" }
    }

    companion object {
        fun removeMascara(numero: String) =
            numero.replace(".", "").replace("-", "")

        fun adicionaMascara(cpf: String): String {
            val cpfSemMascara = removeMascara(cpf)
            require(cpfSemMascara.length == 11) { "CPF com tamanho inválido!" }

            return cpfSemMascara.substring(0, 3) +
                    "." + cpfSemMascara.substring(3, 6) +
                    "." + cpfSemMascara.substring(6, 9) +
                    "-" + cpfSemMascara.substring(9, 11)
        }
    }

}
