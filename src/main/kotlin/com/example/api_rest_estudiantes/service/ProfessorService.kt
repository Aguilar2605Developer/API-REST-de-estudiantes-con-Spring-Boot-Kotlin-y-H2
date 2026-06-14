package com.example.api_rest_estudiantes.service

// Importo los DTOs, repositorio y mapper que voy a necesitar
import com.example.api_rest_estudiantes.dto.ProfessorRequest
import com.example.api_rest_estudiantes.dto.ProfessorResponse
import com.example.api_rest_estudiantes.mappers.ProfessorMapper
import com.example.api_rest_estudiantes.repository.ProfessorRepository
import org.springframework.stereotype.Service

@Service
// Inserto el mapper y el repsotrio que voy a usar
class ProfessorService(
    private val professorRepository: ProfessorRepository,
    private val professorMapper: ProfessorMapper
) {

    // Guardo un profesor en la base de datos y retorno su response
    fun saveProfessor(request: ProfessorRequest): ProfessorResponse {
        // Convierto el request a entidad usando el mapper
        val professorEntity = professorMapper.toEntity(request)
        // Guardo la entidad en la base de datos
        val savedProfessor = professorRepository.save(professorEntity)
        // Convierto la entidad guardada a response y la retorno
        return professorMapper.toResponse(savedProfessor)
    }

    // Obtengo todos los profesores y los retorno como lista de responses
    fun getAllProfessors(): List<ProfessorResponse> {
        return professorRepository.findAll()
            .map { professorMapper.toResponse(it) }
    }
}