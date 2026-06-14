package com.example.api_rest_estudiantes.dto

// Creo el DTO de request para recibir los datos de la materia desde el cliente
class SubjectRequest(
    val name: String,
    // Recibo el id del profesor que dicta esta materia
    val professorId: Long
)

// Creo el DTO de response para enviar los datos de la materia al cliente
class SubjectResponse(
    val id: Long,
    val name: String,
    // Envío el nombre del profesor que dicta esta materia
    val professorName: String
)