package com.example.taskmaster.data.models.entities

sealed class UserTypes(val name: String) {
    object Student : UserTypes("student")
    object Teacher : UserTypes("teacher")
}