package com.example.taskmaster.data.components.converters

import com.google.firebase.Timestamp
import java.util.Date

fun convertDateToTimestamp(date: Date): Timestamp {
    return Timestamp(date)
}
