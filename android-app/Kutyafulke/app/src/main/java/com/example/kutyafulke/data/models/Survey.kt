package com.example.kutyafulke.data.models

data class Survey(
    val time: Long = 0L,
    val id: String = "",
    val dogId: String = "",
    val breedId: String = "0",
    val colors: HashMap<String, Boolean> = hashMapOf(
        "black" to false,
        "brown" to false,
        "cream" to false,
        "gray" to false,
        "red" to false,
        "white" to false,
        "yellow" to false
    ),
    val age: Int = 0,
    val size: Int = 1,
    val feeding: Int = 1,
    val hairLength: Int = 1,
    val hairTexture: Int = 1,
    val grooming: Int = 1,
    val shedding: Int = 1,
    val walking: Int = 1,
    val playfulness: Int = 1,
    val intelligence: Int = 1,
    val kidTolerance: Int = 1,
    val catTolerance: Int = 1,
    val healthProblems: Int = 1,
    val loudness: Int = 1,
    val independence: Int = 1,
    val defensiveness: Int = 1,
)
