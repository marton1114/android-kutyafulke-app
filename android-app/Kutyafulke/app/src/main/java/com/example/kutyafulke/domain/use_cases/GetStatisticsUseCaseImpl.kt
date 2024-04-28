package com.example.kutyafulke.domain.use_cases

import com.example.kutyafulke.data.models.Response
import com.example.kutyafulke.data.models.Survey
import com.example.kutyafulke.data.models.SurveyStats
import com.example.kutyafulke.data.repositories.interfaces.SurveysRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class GetStatisticsUseCaseImpl @Inject constructor (
    private val surveysRepository: SurveysRepository
): GetStatisticsUseCase {
    private fun countColorOccurrences(surveyList: List<Survey>): Map<Map<String, Boolean>, Int> {
        val colorCounts = mutableMapOf<Map<String, Boolean>, Int>()

        for (survey in surveyList) {
            val colors = survey.colors
            if (colorCounts.containsKey(colors)) {
                colorCounts[colors] = colorCounts[colors]!! + 1
            } else {
                colorCounts[colors] = 1
            }
        }

        return colorCounts
    }

    private fun sortColorsByOccurrences(colorOccurrences: Map<Map<String, Boolean>, Int>): List<Map<String, Boolean>> {
        val sortedList = colorOccurrences.toList().sortedByDescending { it.second }
        val resultList = mutableListOf<Map<String, Boolean>>()
        for (entry in sortedList) {
            resultList.add(entry.first)
        }
        return resultList
    }

    override operator fun invoke(breedId: String): Flow<Response<SurveyStats>> = callbackFlow {
        surveysRepository.getSurveysByBreedId(breedId).collect{ response ->
            when (response) {
                is Response.Loading -> {}
                is Response.Failure -> {
                    trySend(Response.Failure(response.e))
                }
                is Response.Success -> {
                    val dataSequence = response.data.asSequence()
                    val surveyStats = SurveyStats(
                        colors = sortColorsByOccurrences(countColorOccurrences(response.data)),
                        size = dataSequence.map { it.size }.average(),
                        feeding = dataSequence.map { it.feeding }.average(),
                        hairLength = dataSequence.map { it.hairLength }.average(),
                        hairTexture = dataSequence.map { it.hairTexture }.average(),
                        grooming = dataSequence.map { it.grooming }.average(),
                        shedding = dataSequence.map { it.shedding }.average(),
                        walking = dataSequence.map { it.walking }.average(),
                        playfulness = dataSequence.map { it.playfulness }.average(),
                        intelligence = dataSequence.map { it.intelligence }.average(),
                        kidTolerance = dataSequence.map { it.kidTolerance }.average(),
                        catTolerance = dataSequence.map { it.catTolerance }.average(),
                        healthProblems = dataSequence.map { it.healthProblems }.average(),
                        loudness = dataSequence.map { it.loudness }.average(),
                        independence = dataSequence.map { it.independence }.average(),
                        defensiveness = dataSequence.map { it.defensiveness }.average(),
                    )
                    trySend(Response.Success(surveyStats))
                }
            }
        }
    }
}