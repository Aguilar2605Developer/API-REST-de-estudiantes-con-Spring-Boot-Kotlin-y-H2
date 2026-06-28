package com.example.api_rest_estudiantes.service

import com.example.api_rest_estudiantes.dto.ProfessorResponse
import com.example.api_rest_estudiantes.dto.SubjectRequest
import com.example.api_rest_estudiantes.dto.SubjectResponse
import com.example.api_rest_estudiantes.entity.Professor
import com.example.api_rest_estudiantes.entity.Subject
import com.example.api_rest_estudiantes.exceptions.BlankNameException
import com.example.api_rest_estudiantes.exceptions.ProfessorNotFound
import com.example.api_rest_estudiantes.exceptions.SubjectNotFound
import com.example.api_rest_estudiantes.mappers.SubjectMapper
import com.example.api_rest_estudiantes.repository.ProfessorRepository
import com.example.api_rest_estudiantes.repository.SubjectRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class SubjectServiceTest {

    @Mock private lateinit var subjectRepository: SubjectRepository
    @Mock private lateinit var professorRepository: ProfessorRepository
    @Mock private lateinit var subjectMapper: SubjectMapper

    @InjectMocks private lateinit var subjectService: SubjectService

    private val professor = Professor(id = 1L, name = "Juan Perez", email = "juan@puce.edu")
    private val professorResponse = ProfessorResponse(id = 1L, name = "Juan Perez", email = "juan@puce.edu")
    private val subject = Subject(id = 1L, name = "Matematicas", code = "MAT101", professor = professor)
    private val subjectResponse = SubjectResponse(id = 1L, name = "Matematicas", code = "MAT101", professor = professorResponse)

    // ===================== saveSubject =====================

    @Test
    fun saveSubject_lanza_BlankNameException_cuando_nombre_vacio() {
        val request = SubjectRequest(name = "", code = "MAT101", professorId = 1L)
        assertThrows(BlankNameException::class.java) {
            subjectService.saveSubject(request)
        }
    }

    @Test
    fun saveSubject_lanza_BlankNameException_cuando_codigo_vacio() {
        val request = SubjectRequest(name = "Matematicas", code = "", professorId = 1L)
        assertThrows(BlankNameException::class.java) {
            subjectService.saveSubject(request)
        }
    }

    @Test
    fun saveSubject_lanza_ProfessorNotFound_cuando_profesor_no_existe() {
        val request = SubjectRequest(name = "Matematicas", code = "MAT101", professorId = 99L)
        `when`(professorRepository.findById(99L)).thenReturn(Optional.empty())
        assertThrows(ProfessorNotFound::class.java) {
            subjectService.saveSubject(request)
        }
    }

    @Test
    fun saveSubject_retorna_SubjectResponse_cuando_datos_validos() {
        val request = SubjectRequest(name = "Matematicas", code = "MAT101", professorId = 1L)
        `when`(professorRepository.findById(1L)).thenReturn(Optional.of(professor))
        `when`(subjectMapper.toEntity(request, professor)).thenReturn(subject)
        `when`(subjectRepository.save(subject)).thenReturn(subject)
        `when`(subjectMapper.toResponse(subject)).thenReturn(subjectResponse)
        val result = subjectService.saveSubject(request)
        assertEquals(1L, result.id)
        assertEquals("Matematicas", result.name)
        assertEquals("MAT101", result.code)
    }

    // ===================== getAllSubjects =====================

    @Test
    fun getAllSubjects_retorna_lista_de_SubjectResponse() {
        `when`(subjectRepository.findAll()).thenReturn(listOf(subject))
        `when`(subjectMapper.toResponse(subject)).thenReturn(subjectResponse)
        val result = subjectService.getAllSubjects()
        assertEquals(1, result.size)
        assertEquals("Matematicas", result[0].name)
    }

    // ===================== getSubjectById =====================

    @Test
    fun getSubjectById_retorna_SubjectResponse_cuando_existe() {
        `when`(subjectRepository.findById(1L)).thenReturn(Optional.of(subject))
        `when`(subjectMapper.toResponse(subject)).thenReturn(subjectResponse)
        val result = subjectService.getSubjectById(1L)
        assertEquals(1L, result.id)
        assertEquals("Matematicas", result.name)
    }

    @Test
    fun getSubjectById_lanza_SubjectNotFound_cuando_no_existe() {
        `when`(subjectRepository.findById(99L)).thenReturn(Optional.empty())
        assertThrows(SubjectNotFound::class.java) {
            subjectService.getSubjectById(99L)
        }
    }

    // ===================== updateSubject =====================

    @Test
    fun updateSubject_lanza_SubjectNotFound_cuando_no_existe() {
        `when`(subjectRepository.findById(99L)).thenReturn(Optional.empty())
        val request = SubjectRequest(name = "Matematicas", code = "MAT101", professorId = 1L)
        assertThrows(SubjectNotFound::class.java) {
            subjectService.updateSubject(99L, request)
        }
    }

    @Test
    fun updateSubject_lanza_BlankNameException_cuando_nombre_vacio() {
        `when`(subjectRepository.findById(1L)).thenReturn(Optional.of(subject))
        val request = SubjectRequest(name = "", code = "MAT101", professorId = 1L)
        assertThrows(BlankNameException::class.java) {
            subjectService.updateSubject(1L, request)
        }
    }

    @Test
    fun updateSubject_lanza_BlankNameException_cuando_codigo_vacio() {
        `when`(subjectRepository.findById(1L)).thenReturn(Optional.of(subject))
        val request = SubjectRequest(name = "Matematicas", code = "", professorId = 1L)
        assertThrows(BlankNameException::class.java) {
            subjectService.updateSubject(1L, request)
        }
    }

    @Test
    fun updateSubject_lanza_ProfessorNotFound_cuando_profesor_no_existe() {
        `when`(subjectRepository.findById(1L)).thenReturn(Optional.of(subject))
        val request = SubjectRequest(name = "Matematicas", code = "MAT101", professorId = 99L)
        `when`(professorRepository.findById(99L)).thenReturn(Optional.empty())
        assertThrows(ProfessorNotFound::class.java) {
            subjectService.updateSubject(1L, request)
        }
    }

    @Test
    fun updateSubject_retorna_SubjectResponse_cuando_datos_validos() {
        val request = SubjectRequest(name = "Matematicas", code = "MAT101", professorId = 1L)
        `when`(subjectRepository.findById(1L)).thenReturn(Optional.of(subject))
        `when`(professorRepository.findById(1L)).thenReturn(Optional.of(professor))
        `when`(subjectRepository.save(any(Subject::class.java))).thenReturn(subject)
        `when`(subjectMapper.toResponse(subject)).thenReturn(subjectResponse)
        val result = subjectService.updateSubject(1L, request)
        assertEquals(1L, result.id)
        assertEquals("Matematicas", result.name)
    }

    // ===================== deleteSubject =====================

    @Test
    fun deleteSubject_lanza_SubjectNotFound_cuando_no_existe() {
        `when`(subjectRepository.findById(99L)).thenReturn(Optional.empty())
        assertThrows(SubjectNotFound::class.java) {
            subjectService.deleteSubject(99L)
        }
    }

    @Test
    fun deleteSubject_elimina_cuando_existe() {
        `when`(subjectRepository.findById(1L)).thenReturn(Optional.of(subject))
        subjectService.deleteSubject(1L)
        verify(subjectRepository).deleteById(1L)
    }
}