package com.example.taskmaster.presentation.other

import android.content.Context
import com.example.taskmaster.R
import java.util.concurrent.TimeUnit

fun getTimeRemaining(context : Context, endDate: Long): String {
    val currentTime = System.currentTimeMillis()
    val timeDifference = endDate - currentTime
    if (timeDifference <= TimeUnit.HOURS.toMillis(24)) {
        val hours = TimeUnit.MILLISECONDS.toHours(timeDifference)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(timeDifference) % 60
        return context.getString(R.string.time_left_hours_and_min, hours, minutes)
    } else {
        val days = TimeUnit.MILLISECONDS.toDays(timeDifference)
        return context.getString(R.string.time_left_days, days)
    }
}