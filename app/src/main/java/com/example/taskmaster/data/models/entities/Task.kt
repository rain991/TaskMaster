package com.example.taskmaster.data.models.entities

import java.util.Date

data class Task(
    val name: String,
    val description : String,
    val groups: List<String>,
    val teacher: String,  // uid
  //  val isActive: Boolean,
    val endDate: Date   //(in app java.util.date)
)
