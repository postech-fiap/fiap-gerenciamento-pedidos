package br.com.fiap.gerenciamentopedidos.api.interceptors

import br.com.fiap.gerenciamentopedidos.domain.exceptions.*
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

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

    @ExceptionHandler(RecursoJaExisteException::class)
    private fun handleRecursoJaExisteException(ex: RecursoJaExisteException): ResponseEntity<ProblemDetail> {
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.message)
        problemDetail.title = HttpStatus.CONFLICT.reasonPhrase
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail)
    }

    @ExceptionHandler(BusinessException::class)
    private fun handleBusinessException(ex: BusinessException): ResponseEntity<ProblemDetail> {
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.message)
        problemDetail.title = HttpStatus.BAD_REQUEST.reasonPhrase
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail)
    }

    @ExceptionHandler(ValidationException::class)
    private fun handleValidationException(ex: ValidationException): ResponseEntity<ProblemDetail> {
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

}
