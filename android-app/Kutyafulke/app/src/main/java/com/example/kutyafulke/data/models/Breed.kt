package com.example.kutyafulke.data.models

data class Breed(
    val id: String = "",
    val hungarianName: String = "",
    val englishName: String = "",
    val colors: List<HashMap<String, Boolean>> = listOf(
        hashMapOf(
            "black" to false,
            "brown" to false,
            "cream" to false,
            "gray" to false,
            "red" to false,
            "white" to false,
            "yellow" to false
        ),
    ),
    val imageUri: String = ""
)