package com.example.api_rest_estudiantes.service

import com.example.api_rest_estudiantes.dto.SubjectRequest
import com.example.api_rest_estudiantes.dto.SubjectResponse
import com.example.api_rest_estudiantes.entity.Subject
import com.example.api_rest_estudiantes.mappers.SubjectMapper
import com.example.api_rest_estudiantes.repository.ProfessorRepository
import com.example.api_rest_estudiantes.repository.SubjectRepository
import org.springframework.stereotype.Service

@Service
class SubjectService(
    private val subjectRepository: SubjectRepository,
    private val professorRepository: ProfessorRepository,
    private val subjectMapper: SubjectMapper
) {

    // Guardo una materia en la base de datos y retorno su response
    fun saveSubject(request: SubjectRequest): SubjectResponse {
        // Busco el profesor por su id, si no existe lanzo una excepción
        val professor = professorRepository.findById(request.professorId)
            .orElseThrow { RuntimeException("Professor not found") }
        // Convierto el request a entidad usando el mapper
        val subjectEntity = subjectMapper.toEntity(request, professor)
        // Guardo la entidad en la base de datos
        val savedSubject = subjectRepository.save(subjectEntity)
        // Convierto la entidad guardada a response y la retorno
        return subjectMapper.toResponse(savedSubject)
    }

    // Obtengo todas las materias y las retorno como lista de responses
    fun getAllSubjects(): List<SubjectResponse> {
        val subjects: List<Subject> = subjectRepository.findAll()
        return subjects.map { subjectMapper.toResponse(it) }
    }
}