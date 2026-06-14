package com.example.api_rest_estudiantes.controller

import com.example.api_rest_estudiantes.dto.EnrollmentRequest
import com.example.api_rest_estudiantes.dto.EnrollmentResponse
import com.example.api_rest_estudiantes.service.EnrollmentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

// Le digo a Spring que esta clase es un controlador REST
@RestController
// Le indico la ruta base para este controlador
@RequestMapping("/api/enrollments")
class EnrollmentController(private val enrollmentService: EnrollmentService) {

    // Creo un enrollment y retorno su response con status 201
    @PostMapping
    fun createEnrollment(@RequestBody request: EnrollmentRequest): ResponseEntity<EnrollmentResponse> {
        val createdEnrollment = enrollmentService.saveEnrollment(request)
        return ResponseEntity(createdEnrollment, HttpStatus.CREATED)
    }

    // Obtengo todos los enrollments y los retorno con status 200
    @GetMapping
    fun getAllEnrollments(): ResponseEntity<List<EnrollmentResponse>> {
        val enrollments = enrollmentService.getAllEnrollments()
        return ResponseEntity.ok(enrollments)
    }
}