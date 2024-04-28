package com.example.kutyafulke.data.models

data class SurveyStats(
val colors: List<Map<String, Boolean>> = emptyList(),
//val age: Double = 0,
val size: Double = 0.0,
val feeding: Double = 0.0,
val hairLength: Double = 0.0,
val hairTexture: Double = 0.0,
val grooming: Double = 0.0,
val shedding: Double = 0.0,
val walking: Double = 0.0,
val playfulness: Double = 0.0,
val intelligence: Double = 0.0,
val kidTolerance: Double = 0.0,
val catTolerance: Double = 0.0,
val healthProblems: Double = 0.0,
val loudness: Double = 0.0,
val independence: Double = 0.0,
val defensiveness: Double = 0.0
)
