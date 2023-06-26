package br.com.fiap.gerenciamentopedidos.domain.cadastro.models

data class Cpf(val numero: String) {

    init {
        require(numero.isNotBlank()) { "CPF não pode ser vazio" }
        require(numero.matches(Regex("\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}"))) { "Formato do CPF é inválido!" }
    }

    companion object {
        fun removeMascara(numero: String) =
            numero.replace(".", "").replace("-", "")

        fun adicionaMascara(cpf: String): String {
            val removeMascara = removeMascara(cpf)
            return removeMascara.substring(0, 3) + "." + cpf.substring(4, 7) + "." + cpf.substring(8, 11) + "-" + cpf.substring(12, 14);
        }
    }

}