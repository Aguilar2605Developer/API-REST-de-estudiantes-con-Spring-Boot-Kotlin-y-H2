package com.example.api_rest_estudiantes.service

import com.example.api_rest_estudiantes.dto.EnrollmentRequest
import com.example.api_rest_estudiantes.dto.EnrollmentResponse
import com.example.api_rest_estudiantes.dto.ProfessorResponse
import com.example.api_rest_estudiantes.dto.StudentResponse
import com.example.api_rest_estudiantes.dto.SubjectResponse
import com.example.api_rest_estudiantes.entity.Enrollment
import com.example.api_rest_estudiantes.entity.Professor
import com.example.api_rest_estudiantes.entity.Student
import com.example.api_rest_estudiantes.entity.Subject
import com.example.api_rest_estudiantes.exceptions.EnrollmentNotFound
import com.example.api_rest_estudiantes.exceptions.StudentNotFoundException
import com.example.api_rest_estudiantes.exceptions.SubjectNotFound
import com.example.api_rest_estudiantes.mappers.EnrollmentMapper
import com.example.api_rest_estudiantes.repository.EnrollmentRepository
import com.example.api_rest_estudiantes.repository.StudentRepository
import com.example.api_rest_estudiantes.repository.SubjectRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class EnrollmentServiceTest {

    @Mock private lateinit var enrollmentRepository: EnrollmentRepository
    @Mock private lateinit var studentRepository: StudentRepository
    @Mock private lateinit var subjectRepository: SubjectRepository
    @Mock private lateinit var enrollmentMapper: EnrollmentMapper

    @InjectMocks private lateinit var enrollmentService: EnrollmentService

    // Objetos reutilizables
    private val professor = Professor(id = 1L, name = "Juan Perez", email = "juan@puce.edu")
    private val student = Student(id = 1L, name = "Ana Lopez", email = "ana@puce.edu")
    private val subject = Subject(id = 1L, name = "Matematicas", code = "MAT101", professor = professor)
    private val enrollment = Enrollment(
        id = 1L,
        createdAt = LocalDateTime.now(),
        status = "INSCRITO",
        student = student,
        subject = subject
    )
    private val professorResponse = ProfessorResponse(id = 1L, name = "Juan Perez", email = "juan@puce.edu")
    private val studentResponse = StudentResponse(id = 1L, name = "Ana Lopez", email = "ana@puce.edu")
    private val subjectResponse = SubjectResponse(id = 1L, name = "Matematicas", code = "MAT101", professor = professorResponse)
    private val enrollmentResponse = EnrollmentResponse(
        id = 1L,
        createdAt = enrollment.createdAt,
        status = "INSCRITO",
        student = studentResponse,
        subject = subjectResponse
    )

    // ===================== saveEnrollment =====================

    @Test
    fun saveEnrollment_lanza_StudentNotFoundException_cuando_estudiante_no_existe() {
        val request = EnrollmentRequest(studentId = 99L, subjectId = 1L)
        `when`(studentRepository.findById(99L)).thenReturn(Optional.empty())
        assertThrows(StudentNotFoundException::class.java) {
            enrollmentService.saveEnrollment(request)
        }
    }

    @Test
    fun saveEnrollment_lanza_SubjectNotFound_cuando_materia_no_existe() {
        val request = EnrollmentRequest(studentId = 1L, subjectId = 99L)
        `when`(studentRepository.findById(1L)).thenReturn(Optional.of(student))
        `when`(subjectRepository.findById(99L)).thenReturn(Optional.empty())
        assertThrows(SubjectNotFound::class.java) {
            enrollmentService.saveEnrollment(request)
        }
    }

    @Test
    fun saveEnrollment_retorna_EnrollmentResponse_cuando_datos_validos() {
        val request = EnrollmentRequest(studentId = 1L, subjectId = 1L)
        `when`(studentRepository.findById(1L)).thenReturn(Optional.of(student))
        `when`(subjectRepository.findById(1L)).thenReturn(Optional.of(subject))
        `when`(enrollmentMapper.toEntity(request, student, subject)).thenReturn(enrollment)
        `when`(enrollmentRepository.save(enrollment)).thenReturn(enrollment)
        `when`(enrollmentMapper.toResponse(enrollment)).thenReturn(enrollmentResponse)
        val result = enrollmentService.saveEnrollment(request)
        assertEquals(1L, result.id)
        assertEquals("INSCRITO", result.status)
    }

    // ===================== getAllEnrollments =====================

    @Test
    fun getAllEnrollments_retorna_lista_de_EnrollmentResponse() {
        `when`(enrollmentRepository.findAll()).thenReturn(listOf(enrollment))
        `when`(enrollmentMapper.toResponse(enrollment)).thenReturn(enrollmentResponse)
        val result = enrollmentService.getAllEnrollments()
        assertEquals(1, result.size)
        assertEquals("INSCRITO", result[0].status)
    }

    // ===================== getEnrollmentById =====================

    @Test
    fun getEnrollmentById_retorna_EnrollmentResponse_cuando_existe() {
        `when`(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment))
        `when`(enrollmentMapper.toResponse(enrollment)).thenReturn(enrollmentResponse)
        val result = enrollmentService.getEnrollmentById(1L)
        assertEquals(1L, result.id)
        assertEquals("INSCRITO", result.status)
    }

    @Test
    fun getEnrollmentById_lanza_EnrollmentNotFound_cuando_no_existe() {
        `when`(enrollmentRepository.findById(99L)).thenReturn(Optional.empty())
        assertThrows(EnrollmentNotFound::class.java) {
            enrollmentService.getEnrollmentById(99L)
        }
    }

    // ===================== updateEnrollment =====================

    @Test
    fun updateEnrollment_lanza_EnrollmentNotFound_cuando_no_existe() {
        `when`(enrollmentRepository.findById(99L)).thenReturn(Optional.empty())
        assertThrows(EnrollmentNotFound::class.java) {
            enrollmentService.updateEnrollment(99L, "ACTIVO")
        }
    }

    @Test
    fun updateEnrollment_retorna_EnrollmentResponse_cuando_datos_validos() {
        `when`(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment))
        `when`(enrollmentRepository.save(any(Enrollment::class.java))).thenReturn(enrollment)
        `when`(enrollmentMapper.toResponse(enrollment)).thenReturn(enrollmentResponse)
        val result = enrollmentService.updateEnrollment(1L, "ACTIVO")
        assertEquals(1L, result.id)
        assertEquals("INSCRITO", result.status)
    }

    // ===================== deleteEnrollment =====================

    @Test
    fun deleteEnrollment_lanza_EnrollmentNotFound_cuando_no_existe() {
        `when`(enrollmentRepository.findById(99L)).thenReturn(Optional.empty())
        assertThrows(EnrollmentNotFound::class.java) {
            enrollmentService.deleteEnrollment(99L)
        }
    }

    @Test
    fun deleteEnrollment_elimina_cuando_existe() {
        `when`(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment))
        enrollmentService.deleteEnrollment(1L)
        verify(enrollmentRepository).deleteById(1L)
    }
}