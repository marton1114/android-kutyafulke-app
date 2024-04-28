package com.example.kutyafulke.data.repositories.interfaces

import com.example.kutyafulke.data.models.Response
import com.example.kutyafulke.data.models.Survey
import kotlinx.coroutines.flow.Flow

interface SurveysRepository {
    fun getSurveysByBreedId(breedId: String): Flow<Response<List<Survey>>>
}