package br.com.fiap.gerenciamentopedidos.api.config.interceptors

import br.com.fiap.gerenciamentopedidos.domain.exceptions.BusinessException
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.infrastructure.exceptions.BaseDeDadosException
import org.springframework.beans.BeanInstantiationException
import org.springframework.http.*
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(Exception::class)
    private fun handleException(ex: Exception): ResponseEntity<ProblemDetail> {
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.message ?: "")
        problemDetail.title = HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail)
    }

    @ExceptionHandler(BaseDeDadosException::class)
    private fun handleBaseDeDadosException(ex: BaseDeDadosException): ResponseEntity<ProblemDetail> {
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.message)
        problemDetail.title = HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail)
    }

    @ExceptionHandler(RecursoNaoEncontradoException::class)
    private fun handleRecursoNaoEncontradoException(ex: RecursoNaoEncontradoException): ResponseEntity<ProblemDetail> {
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.message)
        problemDetail.title = HttpStatus.NOT_FOUND.reasonPhrase
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail)
    }

    @ExceptionHandler(BusinessException::class)
    private fun handleBusinessException(ex: BusinessException): ResponseEntity<ProblemDetail> {
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.message)
        problemDetail.title = HttpStatus.BAD_REQUEST.reasonPhrase
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    private fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<ProblemDetail> {
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.message ?: "")
        problemDetail.title = HttpStatus.BAD_REQUEST.reasonPhrase
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail)
    }

    @ExceptionHandler(BeanInstantiationException::class)
    private fun handleBeanInstantiationException(ex: BeanInstantiationException): ResponseEntity<ProblemDetail> {
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.cause?.message ?: "")
        problemDetail.title = HttpStatus.BAD_REQUEST.reasonPhrase
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail)
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val problemDetail =
            ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.cause?.cause?.message ?: ex.message ?: "")
        problemDetail.title = HttpStatus.BAD_REQUEST.reasonPhrase
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail)
    }
}
