package com.example.api_rest_estudiantes.repository
import com.example.api_rest_estudiantes.entity.Enrollment

// Importo JpaRepository que me da todos los métodos CRUD automáticamente
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

// Le digo a Spring que esta interfaz es un repositorio
@Repository
// Extiendo JpaRepository indicando la entidad y el tipo de su id
interface EnrollmentRepository : JpaRepository<Enrollment, Long>