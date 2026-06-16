package com.example.api_rest_estudiantes.controller

import com.example.api_rest_estudiantes.dto.ProfessorRequest
import com.example.api_rest_estudiantes.dto.ProfessorResponse
import com.example.api_rest_estudiantes.service.ProfessorService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/professors")
class ProfessorController(private val professorService: ProfessorService) {

    @PostMapping
    fun createProfessor(@RequestBody request: ProfessorRequest): ResponseEntity<ProfessorResponse> {
        val createdProfessor = professorService.saveProfessor(request)
        return ResponseEntity(createdProfessor, HttpStatus.CREATED)
    }

    @GetMapping
    fun getAllProfessors(): ResponseEntity<List<ProfessorResponse>> {
        val professors = professorService.getAllProfessors()
        return ResponseEntity.ok(professors)
    }

    // Obtengo un profesor por ID, si no existe el service lanza ProfessorNotFound
    @GetMapping("/{id}")
    fun getProfessorById(@PathVariable id: Long): ResponseEntity<ProfessorResponse> {
        val professor = professorService.getProfessorById(id)
        return ResponseEntity.ok(professor)
    }

    // Actualizo nombre y/o email de un profesor existente
    @PutMapping("/{id}")
    fun updateProfessor(
        @PathVariable id: Long,
        @RequestBody request: ProfessorRequest
    ): ResponseEntity<ProfessorResponse> {
        val updated = professorService.updateProfessor(id, request)
        return ResponseEntity.ok(updated)
    }

    // Elimino un profesor por ID y retorno 204 sin body
    @DeleteMapping("/{id}")
    fun deleteProfessor(@PathVariable id: Long): ResponseEntity<Void> {
        professorService.deleteProfessor(id)
        return ResponseEntity.noContent().build()
    }
}