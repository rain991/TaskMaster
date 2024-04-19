package com.example.taskmaster.data.components.converters

import com.google.firebase.Timestamp
import java.text.DateFormat
import java.util.Date
import java.util.Locale

fun convertDateToTimestamp(date: Date): Timestamp {
    return Timestamp(date)
}

fun Long.toDateString(dateFormat: Int =  DateFormat.MEDIUM): String {
    val df = DateFormat.getDateInstance(dateFormat, Locale.getDefault())
    return df.format(this)
}