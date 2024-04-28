package com.example.kutyafulke.data.repositories.interfaces

import com.example.kutyafulke.data.models.Breed
import com.example.kutyafulke.data.models.Response
import kotlinx.coroutines.flow.Flow

interface BreedsRepository {
    fun getBreeds(): Flow<Response<List<Breed>>>

    fun getBreed(id: String): Flow<Response<Breed>>
}