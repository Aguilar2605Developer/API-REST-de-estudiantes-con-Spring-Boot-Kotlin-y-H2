package com.example.api_rest_estudiantes.service

import com.example.api_rest_estudiantes.dto.StudentRequest
import com.example.api_rest_estudiantes.dto.StudentResponse
import com.example.api_rest_estudiantes.exceptions.BlankNameException
import com.example.api_rest_estudiantes.exceptions.StudentNotFoundException
import com.example.api_rest_estudiantes.mappers.StudentMapper
import com.example.api_rest_estudiantes.repository.StudentRepository
import org.springframework.stereotype.Service

@Service
class StudentService(
    private val studentRepository: StudentRepository,
    private val studentMapper: StudentMapper
) {

    // Guardo un estudiante en la base de datos y retorno su response
    fun saveStudent(request: StudentRequest): StudentResponse {
        // Valido que el nombre no esté vacío antes de guardar
        if (request.name.isBlank()) throw BlankNameException(request.name)
        // Convierto el request a entidad usando el mapper
        val studentEntity = studentMapper.toEntity(request)
        // Guardo la entidad en la base de datos
        val savedStudent = studentRepository.save(studentEntity)
        // Convierto la entidad guardada a response y la retorno
        return studentMapper.toResponse(savedStudent)
    }

    // Obtengo todos los estudiantes y los retorno como lista de responses
    fun getAllStudents(): List<StudentResponse> {
        val students = studentRepository.findAll()
        return students.map { studentMapper.toResponse(it) }
    }

    // Busco un estudiante por su id, si no existe lanzo excepción 404
    fun getStudentById(id: Long): StudentResponse {
        val student = studentRepository.findById(id)
            .orElseThrow { StudentNotFoundException(id) }
        return studentMapper.toResponse(student)
    }

    // Actualizo nombre y/o email de un estudiante existente
    fun updateStudent(id: Long, request: StudentRequest): StudentResponse {
        if (request.name.isBlank()) throw BlankNameException(request.name)
        val student = studentRepository.findById(id)
            .orElseThrow { StudentNotFoundException(id) }
        student.name = request.name
        student.email = request.email
        val saved = studentRepository.save(student)
        return studentMapper.toResponse(saved)
    }

    // Elimino un estudiante por su id
    fun deleteStudent(id: Long) {
        studentRepository.findById(id)
            .orElseThrow { StudentNotFoundException(id) }
        studentRepository.deleteById(id)
    }
}