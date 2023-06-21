package br.com.fiap.gerenciamentopedidos.application.mappers

import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarProdutoRequest
import br.com.fiap.gerenciamentopedidos.application.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.models.Produto
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE)
interface ProdutoMapper {
    fun fromRequest(request: CadastrarProdutoRequest): Produto;
    fun toResponse(model: Produto): ProdutoResponse;
}