package br.com.fiap.gerenciamentopedidos.application.usecases.produto

import br.com.fiap.gerenciamentopedidos.application.interfaces.produto.RemoverProdutoPorIdUseCase
import br.com.fiap.gerenciamentopedidos.domain.dtos.ProdutoDto
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ProdutoRepository

class RemoverProdutoPorIdUseCaseImpl(private val produtoRepository: ProdutoRepository) : RemoverProdutoPorIdUseCase {
    override fun executar(id: Long) {
        val produto =
            produtoRepository.get(id).orElseThrow { RecursoNaoEncontradoException("Produto não encontrado") }.toModel()

        produto.excluir()

        produtoRepository.update(ProdutoDto.fromModel(produto))
    }
}
