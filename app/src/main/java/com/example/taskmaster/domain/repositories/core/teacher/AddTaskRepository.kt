package com.example.taskmaster.domain.repositories.core.teacher

import com.example.taskmaster.data.models.entities.Task

interface AddTaskRepository {
    fun addTask(task : Task)
}