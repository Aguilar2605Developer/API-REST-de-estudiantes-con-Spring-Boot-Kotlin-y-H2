package com.example.api_rest_estudiantes.dto

// Creo el DTO de request para recibir los datos del profesor desde el cliente
class ProfessorRequest(
    val name: String,
    val email: String
)

// Creo el DTO de response para enviar los datos del profesor al cliente
class ProfessorResponse(
    val id: Long,
    val name: String,
    val email: String
)