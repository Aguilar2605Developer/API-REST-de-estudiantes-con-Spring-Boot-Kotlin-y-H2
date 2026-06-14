package com.example.api_rest_estudiantes.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

// Le digo a Spring que esta clase maneja las excepciones de todos los controladores
@RestControllerAdvice
class GlobalExceptionHandler {

    // Capturo la excepción de nombre vacío y retorno un error 400
    @ExceptionHandler(BlankNameException::class)
    fun handleBlankNameException(ex: BlankNameException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)
    }

    // Capturo la excepción de estudiante no encontrado y retorno un error 404
    @ExceptionHandler(StudentNotFoundException::class)
    fun handleStudentNotFoundException(ex: StudentNotFoundException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.NOT_FOUND)
    }

    // Capturo cualquier otra excepción no controlada y retorno un error 500
    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}