package com.example.api_rest_estudiantes.entity


import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

// Le digo a Spring que esta clase es una tabla en la base de datos
@Entity
// Le indico el nombre exacto de la tabla
@Table(name = "subjects")
class Subject(
    // Este campo es la clave primaria
    @Id
    // El id se genera automáticamente (1, 2, 3...)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    val name: String = "",

    // Muchas materias pertenecen a un profesor
    // FetchType.LAZY significa que cargo el profesor solo cuando lo necesito
    @ManyToOne(fetch = FetchType.LAZY)
    val professor: Professor,

    // Una materia puede tener muchos enrollments
    // cascade = ALL significa que si borro la materia, se borran sus enrollments
    // orphanRemoval = true elimina enrollments huérfanos automáticamente
    @OneToMany(mappedBy = "subject", cascade = [CascadeType.ALL], orphanRemoval = true)
    val enrollments: MutableList<Enrollment> = mutableListOf()
)