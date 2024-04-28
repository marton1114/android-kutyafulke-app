package com.example.kutyafulke.domain.use_cases

import com.example.kutyafulke.data.models.Response
import com.example.kutyafulke.data.models.SurveyStats
import kotlinx.coroutines.flow.Flow

interface GetStatisticsUseCase {
    operator fun invoke(breedId: String): Flow<Response<SurveyStats>>
}
