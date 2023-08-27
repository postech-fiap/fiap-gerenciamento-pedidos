package br.com.fiap.gerenciamentopedidos.infrastructure.repositories.service

import br.com.fiap.gerenciamentopedidos.domain.dtos.MercadoPagoOrdemDto
import br.com.fiap.gerenciamentopedidos.domain.dtos.MercadoPagoResponseOrdemDto
import br.com.fiap.gerenciamentopedidos.domain.dtos.PagamentoDto
import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PagamentoService
import br.com.fiap.gerenciamentopedidos.domain.models.Pagamento
import br.com.fiap.gerenciamentopedidos.infrastructure.exceptions.IntegracaoAPIException
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.web.client.RestTemplate
import java.time.OffsetDateTime

private const val HEADER_NAME_AUTH = "Authorization"
private const val ERROR_MESSAGE = "Erro de integração para gerar o pagamento. Detalhes: %s"

class PagamentoServiceImpl(
    private val restTemplate: RestTemplate,
    private val mercadoPagoApiGenerateQrcodeEndpoint: String,
    private val mercadoPagoToken: String
) : PagamentoService {

    override fun gerarPagamento(pedido: PedidoDto): PagamentoDto {
        val url = mercadoPagoApiGenerateQrcodeEndpoint

        val entity = HttpEntity(MercadoPagoOrdemDto.fromDto(pedido), buildHeaders())

        try {
            val response = restTemplate.postForEntity(url, entity, MercadoPagoResponseOrdemDto::class.java)

            if (response.statusCode != HttpStatus.CREATED) {
                throw IntegracaoAPIException(String.format(ERROR_MESSAGE, "[status_code: " + "${response.statusCode}"))
            }

            requireNotNull(response.body) { "A resposta não deve ser nula " }

            return PagamentoDto(
                dataHora = OffsetDateTime.now(),
                status = PagamentoStatus.APROVADO,
                qrCode = response.body!!.qrData,
                valorTotal = pedido.valorTotal
            )

        } catch (ex: IntegracaoAPIException) {
            throw ex
        } catch (ex: Exception) {
            throw IntegracaoAPIException(String.format(ERROR_MESSAGE, ex.message))

        }
    }

    private fun buildHeaders(): HttpHeaders {
        val headers = HttpHeaders()
        headers[HEADER_NAME_AUTH] = mercadoPagoToken
        return headers
    }
}