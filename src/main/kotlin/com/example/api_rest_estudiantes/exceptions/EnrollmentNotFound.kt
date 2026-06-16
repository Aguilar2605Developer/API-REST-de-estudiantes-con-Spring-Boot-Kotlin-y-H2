package com.example.api_rest_estudiantes.exceptions

// Excepción que se lanza cuando no se encuentra un enrollment por su id
class EnrollmentNotFound(id: Long) : RuntimeException("Enrollment con id '$id' no fue encontrado")