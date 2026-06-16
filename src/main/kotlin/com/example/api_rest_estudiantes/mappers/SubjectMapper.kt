package com.example.api_rest_estudiantes.mappers

import com.example.api_rest_estudiantes.dto.ProfessorResponse
import com.example.api_rest_estudiantes.dto.SubjectRequest
import com.example.api_rest_estudiantes.dto.SubjectResponse
import com.example.api_rest_estudiantes.entity.Professor
import com.example.api_rest_estudiantes.entity.Subject
import org.springframework.stereotype.Component

@Component
class SubjectMapper {

    // Convierto un SubjectRequest a una entidad Subject para guardar en la base de datos
    fun toEntity(request: SubjectRequest, professor: Professor): Subject {
        return Subject(
            name = request.name,
            code = request.code,
            professor = professor
        )
    }

    // Convierto una entidad Subject a un SubjectResponse para enviar al cliente
    fun toResponse(subject: Subject): SubjectResponse {
        return SubjectResponse(
            id = subject.id!!,
            name = subject.name,
            code = subject.code,
            professor = ProfessorResponse(
                id = subject.professor.id!!,
                name = subject.professor.name,
                email = subject.professor.email
            )
        )
    }
}