package com.example.api_rest_estudiantes.entity

// Importo las anotaciones necesarias para mapear esta clase a la base de datos
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
// Importo LocalDateTime para manejar fechas y horas
import java.time.LocalDateTime

// Le digo a Spring que esta clase es una tabla en la base de datos
@Entity
// Le indico el nombre exacto de la tabla
@Table(name = "enrollments")
// Uso open class para permitir que Spring pueda crear proxies de esta clase
open class Enrollment(
    // Este campo es la clave primaria
    @Id
    // El id se genera automáticamente (1, 2, 3...)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    // Guardo la fecha y hora exacta en que se creó el enrollment
    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    // Estado del enrollment (activo, inactivo, etc.)
    val status: String = "",

    // Muchos enrollments pertenecen a un Subject
    // FetchType.LAZY significa que cargo el subject solo cuando lo necesito
    @ManyToOne(fetch = FetchType.LAZY)
    val subject: Subject,

    // Muchos enrollments pertenecen a un Student
    // FetchType.LAZY significa que cargo el student solo cuando lo necesito
    @ManyToOne(fetch = FetchType.LAZY)
    val student: Student,
)