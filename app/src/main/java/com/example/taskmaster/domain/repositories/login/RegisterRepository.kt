package com.example.taskmaster.domain.repositories.login

import com.example.taskmaster.data.models.abstractionLayer.User

interface RegisterRepository {
    fun <T: User> registerUser(user: T)
}