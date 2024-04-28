package com.example.kutyafulke.data.alarms

import java.time.LocalDateTime

data class AlarmDataItem(
    val id: String,
    val name: String,
    val time: LocalDateTime,
    val message: String
)
