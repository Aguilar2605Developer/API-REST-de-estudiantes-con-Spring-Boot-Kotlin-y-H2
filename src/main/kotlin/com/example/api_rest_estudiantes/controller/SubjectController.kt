package com.example.api_rest_estudiantes.controller

import com.example.api_rest_estudiantes.dto.SubjectRequest
import com.example.api_rest_estudiantes.dto.SubjectResponse
import com.example.api_rest_estudiantes.service.SubjectService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/subjects")
class SubjectController(private val subjectService: SubjectService) {

    @PostMapping
    fun createSubject(@RequestBody request: SubjectRequest): ResponseEntity<SubjectResponse> {
        val createdSubject = subjectService.saveSubject(request)
        return ResponseEntity(createdSubject, HttpStatus.CREATED)
    }

    @GetMapping
    fun getAllSubjects(): ResponseEntity<List<SubjectResponse>> {
        val subjects = subjectService.getAllSubjects()
        return ResponseEntity.ok(subjects)
    }

    @GetMapping("/{id}")
    fun getSubjectById(@PathVariable id: Long): ResponseEntity<SubjectResponse> {
        val subject = subjectService.getSubjectById(id)
        return ResponseEntity.ok(subject)
    }

    @PutMapping("/{id}")
    fun updateSubject(
        @PathVariable id: Long,
        @RequestBody request: SubjectRequest
    ): ResponseEntity<SubjectResponse> {
        val updated = subjectService.updateSubject(id, request)
        return ResponseEntity.ok(updated)
    }

    @DeleteMapping("/{id}")
    fun deleteSubject(@PathVariable id: Long): ResponseEntity<Void> {
        subjectService.deleteSubject(id)
        return ResponseEntity.noContent().build()
    }
}