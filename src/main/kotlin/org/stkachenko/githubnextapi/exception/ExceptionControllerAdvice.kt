package org.stkachenko.githubnextapi.exception

import io.netty.handler.timeout.TimeoutException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.reactive.function.client.WebClientRequestException
import org.springframework.web.reactive.function.client.WebClientResponseException
import org.springframework.web.server.ServerWebInputException
import org.stkachenko.githubnextapi.logger.log

@ControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler(WebClientResponseException::class)
    fun handleWebClientResponseException(ex: WebClientResponseException): ResponseEntity<String> {
        log.error(ex.message)
        return ResponseEntity(ex.responseBodyAsString, ex.statusCode)
    }

    @ExceptionHandler(TimeoutException::class)
    fun handleTimeoutException(ex: TimeoutException): ResponseEntity<String> {
        log.error(ex.message)
        return ResponseEntity("Request timeout", HttpStatus.REQUEST_TIMEOUT)
    }

    @ExceptionHandler(WebClientRequestException::class)
    fun handleWebClientRequestException(ex: WebClientRequestException): ResponseEntity<String> {
        log.error(ex.message)
        return ResponseEntity("Error during request: ${ex.message}", HttpStatus.SERVICE_UNAVAILABLE)
    }

    @ExceptionHandler(ServerWebInputException::class)
    fun handleServerWebInputException(ex: ServerWebInputException): ResponseEntity<String> {
        log.error(ex.message)
        return ResponseEntity("Invalid input: ${ex.reason}", HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(HttpStatusCodeException::class)
    fun handleHttpStatusCodeException(ex: HttpStatusCodeException): ResponseEntity<String> {
        log.error(ex.message)
        return ResponseEntity("HTTP Status Error: ${ex.statusCode}", ex.statusCode)
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<String> {
        log.error(ex.message)
        return ResponseEntity("An unexpected error occurred: ${ex.message}", HttpStatus.INTERNAL_SERVER_ERROR)
    }
}