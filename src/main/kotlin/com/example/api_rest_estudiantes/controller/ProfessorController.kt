package com.example.api_rest_estudiantes.controller

import com.example.api_rest_estudiantes.dto.ProfessorRequest
import com.example.api_rest_estudiantes.dto.ProfessorResponse
import com.example.api_rest_estudiantes.service.ProfessorService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

// Le digo a Spring que esta clase es un controlador REST
@RestController
// Le indico la ruta base para este controlador
@RequestMapping("/api/professors")
class ProfessorController(private val professorService: ProfessorService) {

    // Creo un profesor y retorno su response con status 201
    @PostMapping
    fun createProfessor(@RequestBody request: ProfessorRequest): ResponseEntity<ProfessorResponse> {
        val createdProfessor = professorService.saveProfessor(request)
        return ResponseEntity(createdProfessor, HttpStatus.CREATED)
    }

    // Obtengo todos los profesores y los retorno con status 200
    @GetMapping
    fun getAllProfessors(): ResponseEntity<List<ProfessorResponse>> {
        val professors = professorService.getAllProfessors()
        return ResponseEntity.ok(professors)
    }
}