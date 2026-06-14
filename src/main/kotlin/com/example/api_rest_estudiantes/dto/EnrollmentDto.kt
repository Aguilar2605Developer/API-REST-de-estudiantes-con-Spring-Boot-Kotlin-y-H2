package com.example.api_rest_estudiantes.dto

import java.time.LocalDateTime

// Creo el DTO de request para recibir los datos del enrollment desde el cliente
class EnrollmentRequest(
    // Recibo los ids del student y subject para crear el enrollment
    val studentId: Long,
    val subjectId: Long,
    val status: String
)

// Creo el DTO de response para enviar los datos del enrollment al cliente
class EnrollmentResponse(
    val id: Long,
    // Envío los nombres del student y subject en lugar de sus ids
    val studentName: String,
    val subjectName: String,
    val status: String,
    // Envío la fecha de creación del enrollment
    val createdAt: LocalDateTime
)