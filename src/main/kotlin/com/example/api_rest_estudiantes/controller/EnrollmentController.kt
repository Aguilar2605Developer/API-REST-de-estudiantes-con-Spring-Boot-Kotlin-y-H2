package com.example.api_rest_estudiantes.controller

import com.example.api_rest_estudiantes.dto.EnrollmentRequest
import com.example.api_rest_estudiantes.dto.EnrollmentResponse
import com.example.api_rest_estudiantes.dto.EnrollmentStatusUpdate
import com.example.api_rest_estudiantes.service.EnrollmentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/enrollments")
class EnrollmentController(private val enrollmentService: EnrollmentService) {

    @PostMapping
    fun createEnrollment(@RequestBody request: EnrollmentRequest): ResponseEntity<EnrollmentResponse> {
        val createdEnrollment = enrollmentService.saveEnrollment(request)
        return ResponseEntity(createdEnrollment, HttpStatus.CREATED)
    }

    @GetMapping
    fun getAllEnrollments(): ResponseEntity<List<EnrollmentResponse>> {
        val enrollments = enrollmentService.getAllEnrollments()
        return ResponseEntity.ok(enrollments)
    }

    @GetMapping("/{id}")
    fun getEnrollmentById(@PathVariable id: Long): ResponseEntity<EnrollmentResponse> {
        val enrollment = enrollmentService.getEnrollmentById(id)
        return ResponseEntity.ok(enrollment)
    }

    @PutMapping("/{id}")
    fun updateEnrollment(
        @PathVariable id: Long,
        @RequestBody request: EnrollmentStatusUpdate
    ): ResponseEntity<EnrollmentResponse> {
        val updated = enrollmentService.updateEnrollment(id, request.status) // fix aquí
        return ResponseEntity.ok(updated)
    }

    @DeleteMapping("/{id}")
    fun deleteEnrollment(@PathVariable id: Long): ResponseEntity<Void> {
        enrollmentService.deleteEnrollment(id)
        return ResponseEntity.noContent().build()
    }
}