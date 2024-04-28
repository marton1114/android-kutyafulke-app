package com.example.kutyafulke.data.models

data class OwnedDog(
    val id: String = "",
    val userId: String = "",
    val breedId: String = "",
    val imageUri: String = "",
    val dogName: String = "",
    val dogWeight: String = "0.0",
    val averageDogWeight: String = "0.0",

    val locked: Boolean = true,
    val traveledWeeklyDistance: Float = 0.0F,
    val neededWeeklyDistance: Float = 0.0F,
    val lastWalkingResetTime: Long = 0,

    val creationTime: Long = 0,
    val neededPills: Int = 1,
    val pillGiven: Boolean = false,
    val nextPillDateInMillis: Long = 0L,

    val verified: Boolean = false,
    val walkingComponentEnabled: Boolean = true,
    val healthComponentEnabled: Boolean = false,
)
