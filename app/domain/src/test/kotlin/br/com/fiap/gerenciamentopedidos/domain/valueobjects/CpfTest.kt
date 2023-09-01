package br.com.fiap.gerenciamentopedidos.domain.valueobjects

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CpfTest {


    @Test
    fun `deve remover a máscara com sucesso quando houver máscara no cpf`() {
        //given
        val cpf = "111.111.111-11"

        //when
        val cpfSemMascara = Cpf.removeMascara(cpf)

        //then
        Assertions.assertEquals("11111111111", cpfSemMascara)
    }

    @Test
    fun `deve retornar o cpf com sucesso quando não houver máscara a ser removida`() {
        //given
        val cpf = "11111111111"

        //when
        val cpfSemMascara = Cpf.removeMascara(cpf)

        //then
        Assertions.assertEquals(cpf, cpfSemMascara)
    }

    @Test
    fun `deve adicionar máscara sucesso quando o cpf possui tamanho válido`() {
        //given
        val cpf = "11111111111"

        //when
        val cpfComMascara = Cpf.adicionaMascara(cpf)

        //then
        Assertions.assertEquals("111.111.111-11", cpfComMascara)
    }

    @Test
    fun `deve retornar o cpf quando possui máscara válida`() {
        //given
        val cpf = "111.111.111-11"

        //when
        val cpfComMascara = Cpf.adicionaMascara(cpf)

        //then
        Assertions.assertEquals("111.111.111-11", cpfComMascara)
    }


    @Test
    fun `deve jogar um erro quando o cpf possui tamanho inválido`() {
        //given
        val cpf = "111111111111"

        //when-then
        val exception = Assertions.assertThrows(IllegalArgumentException::class.java) {
            Cpf.adicionaMascara(cpf)
        }

        //then
        Assertions.assertEquals("CPF com tamanho inválido!", exception.message)
    }

    @Test
    fun `deve jogar um erro quando o cpf possuir número invalidos tamanho inválido`() {
        //given
        val cpf = "111.111.111-11"

        //when-then
        val exception = Assertions.assertThrows(IllegalArgumentException::class.java) {
            Cpf(cpf)
        }

        //then
        Assertions.assertEquals("O Cpf informado é invalido!", exception.message)
    }
}