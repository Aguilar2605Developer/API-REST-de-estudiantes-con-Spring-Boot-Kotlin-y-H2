package com.example.api_rest_estudiantes.mappers

import com.example.api_rest_estudiantes.dto.StudentRequest
import com.example.api_rest_estudiantes.dto.StudentResponse
import com.example.api_rest_estudiantes.entity.Student
import org.springframework.stereotype.Component

@Component
class StudentMapper {

    // Convierto un StudentRequest a una entidad Student para guardar en la base de datos
    fun toEntity(request: StudentRequest): Student {
        return Student(
            name = request.name,
            email = request.email
        )
    }

    // Convierto una entidad Student a un StudentResponse para enviar al cliente
    fun toResponse(student: Student): StudentResponse {
        return StudentResponse(
            id = student.id ?: 0L,
            name = student.name,
            email = student.email
        )
    }
}