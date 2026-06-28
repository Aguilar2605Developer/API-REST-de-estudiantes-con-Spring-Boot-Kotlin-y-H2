package com.example.api_rest_estudiantes.service

import com.example.api_rest_estudiantes.dto.StudentRequest
import com.example.api_rest_estudiantes.dto.StudentResponse
import com.example.api_rest_estudiantes.entity.Student
import com.example.api_rest_estudiantes.exceptions.BlankNameException
import com.example.api_rest_estudiantes.exceptions.StudentNotFoundException
import com.example.api_rest_estudiantes.mappers.StudentMapper
import com.example.api_rest_estudiantes.repository.StudentRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class StudentServiceTest {

    @Mock private lateinit var studentRepository: StudentRepository
    @Mock private lateinit var studentMapper: StudentMapper

    @InjectMocks private lateinit var studentService: StudentService

    private val student = Student(id = 1L, name = "Ana Lopez", email = "ana@puce.edu")
    private val studentResponse = StudentResponse(id = 1L, name = "Ana Lopez", email = "ana@puce.edu")

    // ===================== saveStudent =====================

    @Test
    fun saveStudent_lanza_BlankNameException_cuando_nombre_vacio() {
        val request = StudentRequest(name = "", email = "ana@puce.edu")
        assertThrows(BlankNameException::class.java) {
            studentService.saveStudent(request)
        }
    }

    @Test
    fun saveStudent_retorna_StudentResponse_cuando_datos_validos() {
        val request = StudentRequest(name = "Ana Lopez", email = "ana@puce.edu")
        `when`(studentMapper.toEntity(request)).thenReturn(student)
        `when`(studentRepository.save(student)).thenReturn(student)
        `when`(studentMapper.toResponse(student)).thenReturn(studentResponse)
        val result = studentService.saveStudent(request)
        assertEquals(1L, result.id)
        assertEquals("Ana Lopez", result.name)
    }

    // ===================== getAllStudents =====================

    @Test
    fun getAllStudents_retorna_lista_de_StudentResponse() {
        `when`(studentRepository.findAll()).thenReturn(listOf(student))
        `when`(studentMapper.toResponse(student)).thenReturn(studentResponse)
        val result = studentService.getAllStudents()
        assertEquals(1, result.size)
        assertEquals("Ana Lopez", result[0].name)
    }

    // ===================== getStudentById =====================

    @Test
    fun getStudentById_retorna_StudentResponse_cuando_existe() {
        `when`(studentRepository.findById(1L)).thenReturn(Optional.of(student))
        `when`(studentMapper.toResponse(student)).thenReturn(studentResponse)
        val result = studentService.getStudentById(1L)
        assertEquals(1L, result.id)
        assertEquals("Ana Lopez", result.name)
    }

    @Test
    fun getStudentById_lanza_StudentNotFoundException_cuando_no_existe() {
        `when`(studentRepository.findById(99L)).thenReturn(Optional.empty())
        assertThrows(StudentNotFoundException::class.java) {
            studentService.getStudentById(99L)
        }
    }

    // ===================== updateStudent =====================

    @Test
    fun updateStudent_lanza_BlankNameException_cuando_nombre_vacio() {
        val request = StudentRequest(name = "", email = "ana@puce.edu")
        assertThrows(BlankNameException::class.java) {
            studentService.updateStudent(1L, request)
        }
    }

    @Test
    fun updateStudent_lanza_StudentNotFoundException_cuando_no_existe() {
        val request = StudentRequest(name = "Ana Lopez", email = "ana@puce.edu")
        `when`(studentRepository.findById(99L)).thenReturn(Optional.empty())
        assertThrows(StudentNotFoundException::class.java) {
            studentService.updateStudent(99L, request)
        }
    }

    @Test
    fun updateStudent_retorna_StudentResponse_cuando_datos_validos() {
        val request = StudentRequest(name = "Ana Lopez", email = "ana@puce.edu")
        `when`(studentRepository.findById(1L)).thenReturn(Optional.of(student))
        `when`(studentRepository.save(student)).thenReturn(student)
        `when`(studentMapper.toResponse(student)).thenReturn(studentResponse)
        val result = studentService.updateStudent(1L, request)
        assertEquals(1L, result.id)
        assertEquals("Ana Lopez", result.name)
    }

    // ===================== deleteStudent =====================

    @Test
    fun deleteStudent_lanza_StudentNotFoundException_cuando_no_existe() {
        `when`(studentRepository.findById(99L)).thenReturn(Optional.empty())
        assertThrows(StudentNotFoundException::class.java) {
            studentService.deleteStudent(99L)
        }
    }
// Julian Solorzano
    @Test
    fun deleteStudent_elimina_cuando_existe() {
        `when`(studentRepository.findById(1L)).thenReturn(Optional.of(student))
        studentService.deleteStudent(1L)
        verify(studentRepository).deleteById(1L)
    }
}