package com.example.api_rest_estudiantes.exceptions

// Creo una excepción personalizada que se lanza cuando un nombre está vacío
// RuntimeException me permite lanzarla sin necesidad de declararla en el método
class BlankNameException(name: String) : RuntimeException("El nombre '$name' no puede estar vacío")