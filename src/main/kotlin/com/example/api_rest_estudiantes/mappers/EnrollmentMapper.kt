package com.example.api_rest_estudiantes.mappers

import com.example.api_rest_estudiantes.dto.EnrollmentResponse
import com.example.api_rest_estudiantes.dto.EnrollmentRequest
import com.example.api_rest_estudiantes.dto.StudentResponse
import com.example.api_rest_estudiantes.dto.SubjectResponse
import com.example.api_rest_estudiantes.dto.ProfessorResponse
import com.example.api_rest_estudiantes.entity.Enrollment
import com.example.api_rest_estudiantes.entity.Student
import com.example.api_rest_estudiantes.entity.Subject
import org.springframework.stereotype.Component

@Component
class EnrollmentMapper {

    // Convierto un EnrollmentRequest a una entidad Enrollment
    // El status se asigna automáticamente como "INSCRITO"
    fun toEntity(request: EnrollmentRequest, student: Student, subject: Subject): Enrollment {
        return Enrollment(
            status = "INSCRITO",
            student = student,
            subject = subject
        )
    }

    // Convierto una entidad Enrollment a un EnrollmentResponse
    fun toResponse(enrollment: Enrollment): EnrollmentResponse {
        return EnrollmentResponse(
            id = enrollment.id,
            createdAt = enrollment.createdAt,
            status = enrollment.status,
            // Construyo el objeto completo del estudiante
            student = StudentResponse(
                id = enrollment.student.id ?: 0L,
                name = enrollment.student.name,
                email = enrollment.student.email
            ),
            // Construyo el objeto completo de la materia con su profesor
            subject = SubjectResponse(
                id = enrollment.subject.id,
                name = enrollment.subject.name,
                code = enrollment.subject.code,
                professor = ProfessorResponse(
                    id = enrollment.subject.professor.id,
                    name = enrollment.subject.professor.name,
                    email = enrollment.subject.professor.email
                )
            )
        )
    }
}