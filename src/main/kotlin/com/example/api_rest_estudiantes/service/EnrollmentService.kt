package com.example.api_rest_estudiantes.service

import com.example.api_rest_estudiantes.dto.EnrollmentRequest
import com.example.api_rest_estudiantes.dto.EnrollmentResponse
import com.example.api_rest_estudiantes.entity.Enrollment
import com.example.api_rest_estudiantes.exceptions.EnrollmentNotFound
import com.example.api_rest_estudiantes.exceptions.StudentNotFoundException
import com.example.api_rest_estudiantes.exceptions.SubjectNotFound
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
        // Busco el estudiante por su id, si no existe lanzo excepción 404
        val student = studentRepository.findById(request.studentId)
            .orElseThrow { StudentNotFoundException(request.studentId) }
        // Busco la materia por su id, si no existe lanzo excepción 404
        val subject = subjectRepository.findById(request.subjectId)
            .orElseThrow { SubjectNotFound(request.subjectId) }
        val enrollmentEntity = enrollmentMapper.toEntity(request, student, subject)
        val savedEnrollment = enrollmentRepository.save(enrollmentEntity)
        return enrollmentMapper.toResponse(savedEnrollment)
    }

    // Obtengo todos los enrollments y los retorno como lista de responses
    fun getAllEnrollments(): List<EnrollmentResponse> {
        return enrollmentRepository.findAll()
            .map { enrollmentMapper.toResponse(it) }
    }

    // Busco un enrollment por su id, si no existe lanzo excepción 404
    fun getEnrollmentById(id: Long): EnrollmentResponse {
        val enrollment = enrollmentRepository.findById(id)
            .orElseThrow { EnrollmentNotFound(id) }
        return enrollmentMapper.toResponse(enrollment)
    }

    // Actualizo solo el status de un enrollment existente
    fun updateEnrollment(id: Long, status: String): EnrollmentResponse {
        // Busco el enrollment existente
        val enrollment = enrollmentRepository.findById(id)
            .orElseThrow { EnrollmentNotFound(id) }
        // Creo una nueva entidad con el mismo id y el nuevo status
        val updatedEnrollment = Enrollment(
            id = enrollment.id,
            createdAt = enrollment.createdAt,
            status = status,
            student = enrollment.student,
            subject = enrollment.subject
        )
        val savedEnrollment = enrollmentRepository.save(updatedEnrollment)
        return enrollmentMapper.toResponse(savedEnrollment)
    }

    // Elimino un enrollment por su id
    fun deleteEnrollment(id: Long) {
        // Verifico que el enrollment exista antes de eliminar
        enrollmentRepository.findById(id)
            .orElseThrow { EnrollmentNotFound(id) }
        enrollmentRepository.deleteById(id)
    }
}