package com.example.api_rest_estudiantes.service

import com.example.api_rest_estudiantes.dto.EnrollmentRequest
import com.example.api_rest_estudiantes.dto.EnrollmentResponse
import com.example.api_rest_estudiantes.mappers.EnrollmentMapper
import com.example.api_rest_estudiantes.repository.EnrollmentRepository
import com.example.api_rest_estudiantes.repository.StudentRepository
import com.example.api_rest_estudiantes.repository.SubjectRepository
import org.springframework.stereotype.Service

@Service
class EnrollmentService(
    private val enrollmentRepository: EnrollmentRepository,
    private val studentRepository: StudentRepository,
    private val subjectRepository: SubjectRepository,
    private val enrollmentMapper: EnrollmentMapper
) {

    // Guardo un enrollment en la base de datos y retorno su response
    fun saveEnrollment(request: EnrollmentRequest): EnrollmentResponse {
        // Busco el estudiante por su id, si no existe lanzo una excepción
        val student = studentRepository.findById(request.studentId)
            .orElseThrow { RuntimeException("Student not found") }
        // Busco la materia por su id, si no existe lanzo una excepción
        val subject = subjectRepository.findById(request.subjectId)
            .orElseThrow { RuntimeException("Subject not found") }
        // Convierto el request a entidad usando el mapper
        val enrollmentEntity = enrollmentMapper.toEntity(request, student, subject)
        // Guardo la entidad en la base de datos
        val savedEnrollment = enrollmentRepository.save(enrollmentEntity)
        // Convierto la entidad guardada a response y la retorno
        return enrollmentMapper.toResponse(savedEnrollment)
    }

    // Obtengo todos los enrollments y los retorno como lista de responses
    fun getAllEnrollments(): List<EnrollmentResponse> {
        val enrollments = enrollmentRepository.findAll()
        return enrollments.map { enrollmentMapper.toResponse(it) }
    }
}