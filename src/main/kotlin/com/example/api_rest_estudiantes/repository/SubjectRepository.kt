package com.example.api_rest_estudiantes.repository

import com.example.api_rest_estudiantes.entity.Subject
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SubjectRepository : JpaRepository<Subject, Long>