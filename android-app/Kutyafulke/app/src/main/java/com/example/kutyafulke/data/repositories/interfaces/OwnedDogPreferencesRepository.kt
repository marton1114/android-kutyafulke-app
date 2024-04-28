package com.example.kutyafulke.data.repositories.interfaces

import android.location.Location
import com.example.kutyafulke.data.models.Response
import kotlinx.coroutines.flow.Flow

interface OwnedDogPreferencesRepository {
    fun getDistance(ownedDogId: String): Flow<Response<Float>>
    fun setDistance(ownedDogId: String, distance: Float): Flow<Response<Boolean>>
    fun clear(ownedDogId: String): Flow<Response<Boolean>>
    fun getLastLocation(ownedDogId: String): Flow<Response<Location>>
    fun setLastLocation(ownedDogId: String, lastLocation: Location): Flow<Response<Boolean>>
}