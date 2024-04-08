package com.example.taskmaster.data.components.converters

import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

fun convertLocalDateToDate(localDate: LocalDate): Date {
    val zoneId: ZoneId = ZoneId.systemDefault()
    val instant = localDate.atStartOfDay().atZone(zoneId).toInstant()
    return Date.from(instant)
}