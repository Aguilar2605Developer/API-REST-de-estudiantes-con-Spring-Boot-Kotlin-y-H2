package com.example.api_rest_estudiantes.repository

import com.example.api_rest_estudiantes.entity.Student
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StudentRepository : JpaRepository<Student, Long>