package com.example.api_rest_estudiantes.exceptions

// Excepción que se lanza cuando no se encuentra un profesor por su id
class ProfessorNotFound(id: Long) : RuntimeException("Profesor con id '$id' no fue encontrado")