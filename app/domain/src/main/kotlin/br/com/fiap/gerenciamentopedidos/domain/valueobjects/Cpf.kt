package br.com.fiap.gerenciamentopedidos.domain.valueobjects

data class Cpf(val numero: String) {

    init {
        require(numero.isNotBlank()) { "CPF não pode ser vazio" }
        require(numero.matches(Regex("(\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2})|\\d{11}"))) { "Formato do CPF é inválido!" }
        require(isValidCpf(numero)) { "O Cpf informado é invalido!"}
    }

    private fun isValidCpf(document: String): Boolean {
        if (document.isEmpty()) return false

        return validateNumbers(document)
    }

    private fun validateNumbers(document: String): Boolean {
        val numbers = document.filter { it.isDigit() }.map {
            it.toString().toInt()
        }

        if (numbers.size != 11) return false

        //repeticao
        if (numbers.all { it == numbers[0] }) return false

        //digito 1
        val dv1 = ((0..8).sumOf { (it + 1) * numbers[it] }).rem(11).let {
            if (it >= 10) 0 else it
        }

        val dv2 = ((0..8).sumOf { it * numbers[it] }.let { (it + (dv1 * 9)).rem(11) }).let {
            if (it >= 10) 0 else it
        }

        return numbers[9] == dv1 && numbers[10] == dv2
    }

    fun removeMascara() = removeMascara(numero)

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
