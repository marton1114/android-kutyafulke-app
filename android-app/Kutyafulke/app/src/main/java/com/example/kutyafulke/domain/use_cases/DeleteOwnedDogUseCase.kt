package com.example.kutyafulke.domain.use_cases

import com.example.kutyafulke.data.models.Response
import kotlinx.coroutines.flow.Flow

interface DeleteOwnedDogUseCase {
    operator fun invoke(ownedDogId: String): Flow<Response<Boolean>>
}