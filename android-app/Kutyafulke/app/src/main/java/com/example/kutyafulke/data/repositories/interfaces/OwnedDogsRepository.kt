package com.example.kutyafulke.data.repositories.interfaces

import com.example.kutyafulke.data.models.OwnedDog
import com.example.kutyafulke.data.models.Response
import kotlinx.coroutines.flow.Flow

interface OwnedDogsRepository {
    fun getOwnedDog(id: String): Flow<Response<OwnedDog>>

    fun getOwnedDogsByUserId(userId: String): Flow<Response<List<OwnedDog>>>

    fun addOwnedDog(ownedDog: OwnedDog): Flow<Response<Boolean>>

    fun deleteOwnedDogById(ownedDogId: String): Flow<Response<Boolean>>

    fun updateOwnedDog(ownedDog: OwnedDog): Flow<Response<Boolean>>
}