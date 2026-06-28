package com.example.api_rest_estudiantes.service

import com.example.api_rest_estudiantes.dto.ProfessorRequest
import com.example.api_rest_estudiantes.dto.ProfessorResponse
import com.example.api_rest_estudiantes.entity.Professor
import com.example.api_rest_estudiantes.exceptions.BlankNameException
import com.example.api_rest_estudiantes.exceptions.ProfessorNotFound
import com.example.api_rest_estudiantes.mappers.ProfessorMapper
import com.example.api_rest_estudiantes.repository.ProfessorRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class ProfessorServiceTest {

    @Mock private lateinit var professorRepository: ProfessorRepository
    @Mock private lateinit var professorMapper: ProfessorMapper

    @InjectMocks private lateinit var professorService: ProfessorService

    private val professor = Professor(id = 1L, name = "Juan Perez", email = "juan@puce.edu")
    private val professorResponse = ProfessorResponse(id = 1L, name = "Juan Perez", email = "juan@puce.edu")

    // ===================== saveProfessor =====================

    @Test
    fun saveProfessor_lanza_BlankNameException_cuando_nombre_vacio() {
        val request = ProfessorRequest(name = "", email = "juan@puce.edu")
        assertThrows(BlankNameException::class.java) {
            professorService.saveProfessor(request)
        }
    }

    @Test
    fun saveProfessor_retorna_ProfessorResponse_cuando_datos_validos() {
        val request = ProfessorRequest(name = "Juan Perez", email = "juan@puce.edu")
        `when`(professorMapper.toEntity(request)).thenReturn(professor)
        `when`(professorRepository.save(professor)).thenReturn(professor)
        `when`(professorMapper.toResponse(professor)).thenReturn(professorResponse)
        val result = professorService.saveProfessor(request)
        assertEquals(1L, result.id)
        assertEquals("Juan Perez", result.name)
    }

    // ===================== getAllProfessors =====================

    @Test
    fun getAllProfessors_retorna_lista_de_ProfessorResponse() {
        `when`(professorRepository.findAll()).thenReturn(listOf(professor))
        `when`(professorMapper.toResponse(professor)).thenReturn(professorResponse)
        val result = professorService.getAllProfessors()
        assertEquals(1, result.size)
        assertEquals("Juan Perez", result[0].name)
    }

    // ===================== getProfessorById =====================

    @Test
    fun getProfessorById_retorna_ProfessorResponse_cuando_existe() {
        `when`(professorRepository.findById(1L)).thenReturn(Optional.of(professor))
        `when`(professorMapper.toResponse(professor)).thenReturn(professorResponse)
        val result = professorService.getProfessorById(1L)
        assertEquals(1L, result.id)
        assertEquals("Juan Perez", result.name)
    }

    @Test
    fun getProfessorById_lanza_ProfessorNotFound_cuando_no_existe() {
        `when`(professorRepository.findById(99L)).thenReturn(Optional.empty())
        assertThrows(ProfessorNotFound::class.java) {
            professorService.getProfessorById(99L)
        }
    }

    // ===================== updateProfessor =====================

    @Test
    fun updateProfessor_lanza_ProfessorNotFound_cuando_no_existe() {
        `when`(professorRepository.findById(99L)).thenReturn(Optional.empty())
        val request = ProfessorRequest(name = "Juan Perez", email = "juan@puce.edu")
        assertThrows(ProfessorNotFound::class.java) {
            professorService.updateProfessor(99L, request)
        }
    }

    @Test
    fun updateProfessor_lanza_BlankNameException_cuando_nombre_vacio() {
        `when`(professorRepository.findById(1L)).thenReturn(Optional.of(professor))
        val request = ProfessorRequest(name = "", email = "juan@puce.edu")
        assertThrows(BlankNameException::class.java) {
            professorService.updateProfessor(1L, request)
        }
    }

    @Test
    fun updateProfessor_retorna_ProfessorResponse_cuando_datos_validos() {
        val request = ProfessorRequest(name = "Juan Perez", email = "juan@puce.edu")
        `when`(professorRepository.findById(1L)).thenReturn(Optional.of(professor))
        `when`(professorRepository.save(any(Professor::class.java))).thenReturn(professor)
        `when`(professorMapper.toResponse(professor)).thenReturn(professorResponse)
        val result = professorService.updateProfessor(1L, request)
        assertEquals(1L, result.id)
        assertEquals("Juan Perez", result.name)
    }

    // ===================== deleteProfessor =====================

    @Test
    fun deleteProfessor_lanza_ProfessorNotFound_cuando_no_existe() {
        `when`(professorRepository.findById(99L)).thenReturn(Optional.empty())
        assertThrows(ProfessorNotFound::class.java) {
            professorService.deleteProfessor(99L)
        }
    }

    @Test
    fun deleteProfessor_elimina_cuando_existe() {
        `when`(professorRepository.findById(1L)).thenReturn(Optional.of(professor))
        professorService.deleteProfessor(1L)
        verify(professorRepository).deleteById(1L)
    }
}