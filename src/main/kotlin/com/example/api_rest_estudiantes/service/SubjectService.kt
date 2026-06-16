package com.example.api_rest_estudiantes.service

import com.example.api_rest_estudiantes.dto.SubjectRequest
import com.example.api_rest_estudiantes.dto.SubjectResponse
import com.example.api_rest_estudiantes.entity.Subject
import com.example.api_rest_estudiantes.exceptions.BlankNameException
import com.example.api_rest_estudiantes.exceptions.ProfessorNotFound
import com.example.api_rest_estudiantes.exceptions.SubjectNotFound
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
        // Valido que el nombre y código no estén vacíos
        if (request.name.isBlank()) throw BlankNameException(request.name)
        if (request.code.isBlank()) throw BlankNameException(request.code)
        // Busco el profesor por su id, si no existe lanzo excepción 404
        val professor = professorRepository.findById(request.professorId)
            .orElseThrow { ProfessorNotFound(request.professorId) }
        val subjectEntity = subjectMapper.toEntity(request, professor)
        val savedSubject = subjectRepository.save(subjectEntity)
        return subjectMapper.toResponse(savedSubject)
    }

    // Obtengo todas las materias y las retorno como lista de responses
    fun getAllSubjects(): List<SubjectResponse> {
        val subjects: List<Subject> = subjectRepository.findAll()
        return subjects.map { subjectMapper.toResponse(it) }
    }

    // Busco una materia por su id, si no existe lanzo excepción 404
    fun getSubjectById(id: Long): SubjectResponse {
        val subject = subjectRepository.findById(id)
            .orElseThrow { SubjectNotFound(id) }
        return subjectMapper.toResponse(subject)
    }

    // Actualizo nombre, código y/o profesor de una materia existente
    fun updateSubject(id: Long, request: SubjectRequest): SubjectResponse {
        // Verifico que la materia exista
        subjectRepository.findById(id)
            .orElseThrow { SubjectNotFound(id) }
        if (request.name.isBlank()) throw BlankNameException(request.name)
        if (request.code.isBlank()) throw BlankNameException(request.code)
        // Busco el profesor nuevo si cambió
        val professor = professorRepository.findById(request.professorId)
            .orElseThrow { ProfessorNotFound(request.professorId) }
        val updatedSubject = Subject(id = id, name = request.name, code = request.code, professor = professor)
        val savedSubject = subjectRepository.save(updatedSubject)
        return subjectMapper.toResponse(savedSubject)
    }

    // Elimino una materia por su id
    fun deleteSubject(id: Long) {
        // Verifico que la materia exista antes de eliminar
        subjectRepository.findById(id)
            .orElseThrow { SubjectNotFound(id) }
        subjectRepository.deleteById(id)
    }
}