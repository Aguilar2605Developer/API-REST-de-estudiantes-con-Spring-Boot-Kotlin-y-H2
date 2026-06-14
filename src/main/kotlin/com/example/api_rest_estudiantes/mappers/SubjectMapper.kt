package com.example.api_rest_estudiantes.mappers
import com.example.api_rest_estudiantes.dto.SubjectRequest
import com.example.api_rest_estudiantes.dto.SubjectResponse
import com.example.api_rest_estudiantes.entity.Subject
import org.springframework.stereotype.Component
import com.example.api_rest_estudiantes.entity.Professor
// Le digo a Spring que esta clase es un componente para que la pueda inyectar
@Component
class SubjectMapper {

    // Convierto un SubjectRequest a una entidad Subject para guardar en la base de datos
    fun toEntity(request: SubjectRequest, professor: Professor): Subject {
        return Subject(
            name = request.name,
            professor = professor
        )
    }

    // Convierto una entidad Subject a un SubjectResponse para enviar al cliente
    fun toResponse(subject: Subject): SubjectResponse {
        return SubjectResponse(
            id = subject.id,
            name = subject.name,
            // Envío el nombre del profesor que dicta esta materia
            professorName = subject.professor.name
        )
    }
}