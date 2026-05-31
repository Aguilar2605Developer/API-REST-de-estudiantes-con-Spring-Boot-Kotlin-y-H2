package com.example.api_rest_estudiantes.service

import com.example.api_rest_estudiantes.dto.StudentRequest
import com.example.api_rest_estudiantes.dto.StudentResponse
import com.example.api_rest_estudiantes.entity.Student
import com.example.api_rest_estudiantes.repository.StudentRepository
import org.springframework.stereotype.Service

@Service
class StudentService(private val studentRepository: StudentRepository) {


    fun saveStudent(request: StudentRequest): StudentResponse {
        val studentEntity = Student(
            name = request.name,
            email = request.email
        )

        val savedStudent = studentRepository.save(studentEntity)

        return StudentResponse(
            id = savedStudent.id ?: 0L,
            name = savedStudent.name,
            email = savedStudent.email
        )
    }


    fun getAllStudents(): List<StudentResponse> {
        val students = studentRepository.findAll()

        return students.map { student ->
            StudentResponse(
                id = student.id ?: 0L,
                name = student.name,
                email = student.email
            )
        }
    }
}