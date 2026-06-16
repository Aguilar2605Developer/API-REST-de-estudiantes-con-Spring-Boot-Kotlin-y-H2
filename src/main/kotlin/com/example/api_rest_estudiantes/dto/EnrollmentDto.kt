package com.example.api_rest_estudiantes.dto

import java.time.LocalDateTime

// DTO para recibir los datos de un enrollment desde el cliente
class EnrollmentRequest(
    // Id del estudiante a inscribir
    val studentId: Long,
    // Id de la materia en la que se inscribe
    val subjectId: Long
    // Nota: el status no viene del cliente, se asigna automáticamente como "INSCRITO"
)

// DTO para enviar los datos de un enrollment al cliente
class EnrollmentResponse(
    val id: Long,
    // Fecha y hora de creación del enrollment
    val createdAt: LocalDateTime,
    val status: String,
    // Objeto completo del estudiante
    val student: StudentResponse,
    // Objeto completo de la materia con su profesor
    val subject: SubjectResponse
)
data class EnrollmentStatusUpdate(
    val status: String
)