package br.com.fiap.gerenciamentopedidos.application.usecases

import br.com.fiap.gerenciamentopedidos.application.interfaces.UseCase
import br.com.fiap.gerenciamentopedidos.application.mappers.ProdutoMapper
import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarProdutoRequest
import br.com.fiap.gerenciamentopedidos.application.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.interfaces.services.ProdutoService
import org.mapstruct.factory.Mappers

class CadastrarProdutoUseCase(val service: ProdutoService) : UseCase {

    val mapper: ProdutoMapper = Mappers.getMapper(ProdutoMapper::class.java)
    fun executar(request: CadastrarProdutoRequest): ProdutoResponse {
        return mapper.toResponse(service.add(mapper.fromRequest(request)))
    }
}
