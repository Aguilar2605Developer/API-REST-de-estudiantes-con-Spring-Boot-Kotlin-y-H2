package com.example.api_rest_estudiantes.controller

import com.example.api_rest_estudiantes.dto.StudentRequest
import com.example.api_rest_estudiantes.dto.StudentResponse
import com.example.api_rest_estudiantes.service.StudentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/students")
class StudentController(private val studentService: StudentService) {

    // Creo un estudiante y retorno su response con status 201
    @PostMapping
    fun createStudent(@RequestBody request: StudentRequest): ResponseEntity<StudentResponse> {
        val createdStudent = studentService.saveStudent(request)
        return ResponseEntity(createdStudent, HttpStatus.CREATED)
    }

    // Obtengo todos los estudiantes y los retorno con status 200
    @GetMapping
    fun getAllStudents(): ResponseEntity<List<StudentResponse>> {
        val students = studentService.getAllStudents()
        return ResponseEntity.ok(students)
    }
}