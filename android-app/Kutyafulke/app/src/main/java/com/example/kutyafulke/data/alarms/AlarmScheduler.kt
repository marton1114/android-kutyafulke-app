package com.example.kutyafulke.data.alarms

import android.annotation.SuppressLint

interface AlarmScheduler {
    @SuppressLint("ScheduleExactAlarm")
    fun schedule(alarm: AlarmDataItem)

    fun cancel(alarm: AlarmDataItem)
}