package com.example.api_rest_estudiantes.mappers
import com.example.api_rest_estudiantes.dto.EnrollmentRequest
import com.example.api_rest_estudiantes.dto.EnrollmentResponse
import com.example.api_rest_estudiantes.entity.Enrollment
import com.example.api_rest_estudiantes.entity.Student
import com.example.api_rest_estudiantes.entity.Subject
import org.springframework.stereotype.Component

@Component
class EnrollmentMapper {

    // Convierto un EnrollmentRequest a una entidad Enrollment para guardar en la base de datos
    fun toEntity(request: EnrollmentRequest, student: Student, subject: Subject): Enrollment {
        return Enrollment(
            status = request.status,
            // Asigno el student y subject que me pasan como parámetro
            student = student,
            subject = subject
        )
    }

    // Convierto una entidad Enrollment a un EnrollmentResponse para enviar al cliente
    fun toResponse(enrollment: Enrollment): EnrollmentResponse {
        return EnrollmentResponse(
            id = enrollment.id,
            // Envío el nombre del student y subject en lugar de sus ids
            studentName = enrollment.student.name,
            subjectName = enrollment.subject.name,
            status = enrollment.status,
            createdAt = enrollment.createdAt
        )
    }
}