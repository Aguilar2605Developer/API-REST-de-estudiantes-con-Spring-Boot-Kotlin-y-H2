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

    @PostMapping
    fun createStudent(@RequestBody request: StudentRequest): ResponseEntity<StudentResponse> {
        val createdStudent = studentService.saveStudent(request)
        return ResponseEntity(createdStudent, HttpStatus.CREATED)
    }

    @GetMapping
    fun getAllStudents(): ResponseEntity<List<StudentResponse>> {
        val students = studentService.getAllStudents()
        return ResponseEntity.ok(students)
    }

    // Obtengo un estudiante por ID, si no existe el service lanza StudentNotFoundException
    @GetMapping("/{id}")
    fun getStudentById(@PathVariable id: Long): ResponseEntity<StudentResponse> {
        val student = studentService.getStudentById(id)
        return ResponseEntity.ok(student)
    }

    // Actualizo nombre y/o email de un estudiante existente
    @PutMapping("/{id}")
    fun updateStudent(
        @PathVariable id: Long,
        @RequestBody request: StudentRequest
    ): ResponseEntity<StudentResponse> {
        val updated = studentService.updateStudent(id, request)
        return ResponseEntity.ok(updated)
    }

    // Elimino un estudiante por ID y retorno 204 sin body
    @DeleteMapping("/{id}")
    fun deleteStudent(@PathVariable id: Long): ResponseEntity<Void> {
        studentService.deleteStudent(id)
        return ResponseEntity.noContent().build()
    }
}