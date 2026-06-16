package com.example.api_rest_estudiantes.exceptions

// Excepción que se lanza cuando no se encuentra una materia por su id
class SubjectNotFound(id: Long) : RuntimeException("Materia con id '$id' no fue encontrada")