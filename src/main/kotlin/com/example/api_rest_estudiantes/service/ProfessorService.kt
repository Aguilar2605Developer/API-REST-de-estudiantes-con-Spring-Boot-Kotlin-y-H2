package com.example.api_rest_estudiantes.service

import com.example.api_rest_estudiantes.dto.ProfessorRequest
import com.example.api_rest_estudiantes.dto.ProfessorResponse
import com.example.api_rest_estudiantes.entity.Professor
import com.example.api_rest_estudiantes.exceptions.BlankNameException
import com.example.api_rest_estudiantes.exceptions.ProfessorNotFound
import com.example.api_rest_estudiantes.mappers.ProfessorMapper
import com.example.api_rest_estudiantes.repository.ProfessorRepository
import org.springframework.stereotype.Service

@Service
class ProfessorService(
    private val professorRepository: ProfessorRepository,
    private val professorMapper: ProfessorMapper
) {

    // Guardo un profesor en la base de datos y retorno su response
    fun saveProfessor(request: ProfessorRequest): ProfessorResponse {
        // Valido que el nombre no esté vacío antes de guardar
        if (request.name.isBlank()) throw BlankNameException(request.name)
        val professorEntity = professorMapper.toEntity(request)
        val savedProfessor = professorRepository.save(professorEntity)
        return professorMapper.toResponse(savedProfessor)
    }

    // Obtengo todos los profesores y los retorno como lista de responses
    fun getAllProfessors(): List<ProfessorResponse> {
        return professorRepository.findAll()
            .map { professorMapper.toResponse(it) }
    }

    // Busco un profesor por su id, si no existe lanzo excepción 404
    fun getProfessorById(id: Long): ProfessorResponse {
        val professor = professorRepository.findById(id)
            .orElseThrow { ProfessorNotFound(id) }
        return professorMapper.toResponse(professor)
    }

    // Actualizo nombre y email de un profesor existente
    fun updateProfessor(id: Long, request: ProfessorRequest): ProfessorResponse {
        // Verifico que el profesor exista
        professorRepository.findById(id)
            .orElseThrow { ProfessorNotFound(id) }
        if (request.name.isBlank()) throw BlankNameException(request.name)
        // Creo una nueva entidad con el mismo id y los nuevos datos
        val updatedProfessor = Professor(id = id, name = request.name, email = request.email)
        val savedProfessor = professorRepository.save(updatedProfessor)
        return professorMapper.toResponse(savedProfessor)
    }

    // Elimino un profesor por su id
    fun deleteProfessor(id: Long) {
        // Verifico que el profesor exista antes de eliminar
        professorRepository.findById(id)
            .orElseThrow { ProfessorNotFound(id) }
        professorRepository.deleteById(id)
    }
}