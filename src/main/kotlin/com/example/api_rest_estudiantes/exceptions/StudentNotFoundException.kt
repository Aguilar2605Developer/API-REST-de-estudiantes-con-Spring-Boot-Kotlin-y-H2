package com.example.api_rest_estudiantes.exceptions

// Creo una excepción personalizada que se lanza cuando no encuentro un estudiante
// Le paso el id para indicar cual estudiante no fue encontrado
class StudentNotFoundException(id: Long) : RuntimeException("Estudiante con id '$id' no fue encontrado")