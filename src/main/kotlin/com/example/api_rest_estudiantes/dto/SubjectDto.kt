package com.example.api_rest_estudiantes.dto

// DTO para recibir los datos de una materia desde el cliente
class SubjectRequest(
    // Nombre de la materia
    val name: String,
    // Código único de la materia
    val code: String,
    // Id del profesor que dicta esta materia
    val professorId: Long
)

// DTO para enviar los datos de una materia al cliente
class SubjectResponse(
    val id: Long,
    val name: String,
    // Código único de la materia
    val code: String,
    // Objeto completo del profesor en lugar de solo su nombre
    val professor: ProfessorResponse
)