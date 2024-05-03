package com.example.taskmaster.data.models.entities

import com.example.taskmaster.data.models.abstractionLayer.User

data class Teacher(
    override val userType : String,
    override val email: String,
    override val password: String,
    override val name: String,
    override val surname: String,
    val groups: List<String>,  // groups IDs
    val tasks: List<String>
) : User()
{
    constructor() : this("teacher", "", "", "", "", listOf<String>(), listOf<String>() )
}
