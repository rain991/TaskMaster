package com.example.taskmaster.data.models.entities

data class Task(
    val name: String,
    val description: String,
    val groups: List<String>,
    val teacher: String,  // uid
    val relatedFilesURL: List<String>, // it is list of URL firebase storage reference, dont use this for local URI
    val endDate: Long   // (in app java.util.date or timeMillis)
){
    constructor() : this("", "", listOf<String>(), "", listOf<String>(), 0)
}

