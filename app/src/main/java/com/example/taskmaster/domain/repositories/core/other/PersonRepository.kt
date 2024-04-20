package com.example.taskmaster.domain.repositories.core.other

import com.example.taskmaster.data.models.abstractionLayer.User

interface PersonRepository {
    suspend fun findPersonUIDByEmail(email : String) : String
    suspend fun getCurrentUserType(uid: String) : String
    suspend fun getCurrentUser(uid : String) : User?
}