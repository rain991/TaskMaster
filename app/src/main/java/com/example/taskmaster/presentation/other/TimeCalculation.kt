package com.example.taskmaster.presentation.other

import java.util.concurrent.TimeUnit

fun getTimeRemaining(endDate: Long): String {
    val currentTime = System.currentTimeMillis()
    val timeDifference = endDate - currentTime
    if (timeDifference <= TimeUnit.HOURS.toMillis(24)) {
        val hours = TimeUnit.MILLISECONDS.toHours(timeDifference)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(timeDifference) % 60
        return "Time left: $hours hours and $minutes min."
    } else {
        val days = TimeUnit.MILLISECONDS.toDays(timeDifference)
        return "Time left: $days days"
    }
}