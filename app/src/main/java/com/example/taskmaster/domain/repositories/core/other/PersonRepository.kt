package com.example.taskmaster.domain.repositories.core.other

interface PersonRepository {
    suspend fun findPersonUIDByEmail(email : String) : String
}