package com.example.taskmaster.data.components

import java.util.regex.Pattern

fun validateEmail(email: String): Boolean {
    val pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}\$")
    val matcher = pattern.matcher(email)
    return matcher.matches()
}