package com.example.api_rest_estudiantes.mappers
import com.example.api_rest_estudiantes.dto.ProfessorRequest
import com.example.api_rest_estudiantes.dto.ProfessorResponse
import com.example.api_rest_estudiantes.entity.Professor
import org.springframework.stereotype.Component

// Le digo a Spring que esta clase es un componente para que la pueda inyectar
@Component
class ProfessorMapper {

    // Convierto un ProfessorRequest a una entidad Professor para guardar en la base de datos
    fun toEntity(request: ProfessorRequest): Professor {
        return Professor(
            name = request.name,
            email = request.email
        )
    }

    // Convierto una entidad Professor a un ProfessorResponse para enviar al cliente
    fun toResponse(professor: Professor): ProfessorResponse {
        return ProfessorResponse(
            id = professor.id,
            name = professor.name,
            email = professor.email
        )
    }
}