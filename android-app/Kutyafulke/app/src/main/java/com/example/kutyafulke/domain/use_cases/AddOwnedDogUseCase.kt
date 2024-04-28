package com.example.kutyafulke.domain.use_cases

import android.net.Uri
import com.example.kutyafulke.data.models.Response
import kotlinx.coroutines.flow.Flow

interface AddOwnedDogUseCase {
    operator fun invoke(uri: Uri, breedId: String): Flow<Response<Boolean>>
}