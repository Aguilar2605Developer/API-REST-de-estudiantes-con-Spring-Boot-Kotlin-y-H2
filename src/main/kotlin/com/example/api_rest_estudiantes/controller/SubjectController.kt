package com.example.api_rest_estudiantes.controller

import com.example.api_rest_estudiantes.dto.SubjectRequest
import com.example.api_rest_estudiantes.dto.SubjectResponse
import com.example.api_rest_estudiantes.service.SubjectService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

// Le digo a Spring que esta clase es un controlador REST
@RestController
// Le indico la ruta base para este controlador
@RequestMapping("/api/subjects")
class SubjectController(private val subjectService: SubjectService) {

    // Creo una materia y retorno su response con status 201
    @PostMapping
    fun createSubject(@RequestBody request: SubjectRequest): ResponseEntity<SubjectResponse> {
        val createdSubject = subjectService.saveSubject(request)
        return ResponseEntity(createdSubject, HttpStatus.CREATED)
    }

    // Obtengo todas las materias y las retorno con status 200
    @GetMapping
    fun getAllSubjects(): ResponseEntity<List<SubjectResponse>> {
        val subjects = subjectService.getAllSubjects()
        return ResponseEntity.ok(subjects)
    }
}