package com.example.taskmaster.data.models.abstractionLayer

abstract class User {
    abstract val userType: String
    abstract val email: String
    abstract val password: String
    abstract val name: String
    abstract val surname: String
}