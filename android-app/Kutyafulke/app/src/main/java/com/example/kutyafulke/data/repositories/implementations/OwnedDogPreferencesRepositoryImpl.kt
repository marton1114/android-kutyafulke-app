package com.example.kutyafulke.data.repositories.implementations

import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import com.example.kutyafulke.data.models.Response
import com.example.kutyafulke.data.repositories.interfaces.OwnedDogPreferencesRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class OwnedDogPreferencesRepositoryImpl @Inject constructor(
    private val context: Context
): OwnedDogPreferencesRepository {
    private lateinit var sharedPreferences: SharedPreferences

    override fun getDistance(ownedDogId: String) = callbackFlow {
        sharedPreferences = context.getSharedPreferences(ownedDogId, Context.MODE_PRIVATE)
        val distance = sharedPreferences.getFloat("distance", Float.NaN)

        if (!distance.isNaN()) {
            trySend(Response.Success(distance))
        } else {
            trySend(Response.Failure(Exception("The value is not initialised.")))
        }

        awaitClose()
    }

    override fun setDistance(ownedDogId: String, distance: Float) = callbackFlow {
        sharedPreferences = context.getSharedPreferences(ownedDogId, Context.MODE_PRIVATE)
        val isSuccessful = sharedPreferences.edit().putFloat("distance", distance).commit()

        if (isSuccessful) {
            trySend(Response.Success(true))
        } else {
            trySend(Response.Failure(Exception("Can not commit changes.")))
        }
        awaitClose()
    }

    override fun getLastLocation(ownedDogId: String) = callbackFlow {
        sharedPreferences = context.getSharedPreferences(ownedDogId, Context.MODE_PRIVATE)

        val latitude = sharedPreferences.getFloat("lastLocationLatitude", Float.NaN)
        val longitude = sharedPreferences.getFloat("lastLocationLongitude", Float.NaN)

        if (latitude.isNaN() or longitude.isNaN()) {
            trySend(Response.Failure(Exception("The value is not initialised.")))
        } else {
            val lastLocation = Location("").also {
                it.latitude = latitude.toDouble()
                it.longitude = longitude.toDouble()
            }

            trySend(Response.Success(lastLocation))
        }

        awaitClose()
    }

    override fun setLastLocation(ownedDogId: String, lastLocation: Location) = callbackFlow {
        sharedPreferences = context.getSharedPreferences(ownedDogId, Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .putFloat("lastLocationLatitude", lastLocation.latitude.toFloat())
            .putFloat("lastLocationLongitude", lastLocation.longitude.toFloat())
            .apply()

        send(Response.Success(true))
        awaitClose()
    }

    override fun clear(ownedDogId: String): Flow<Response<Boolean>> = callbackFlow {
        sharedPreferences = context.getSharedPreferences(ownedDogId, Context.MODE_PRIVATE)
        val isSuccessful = sharedPreferences.edit().clear().apply()
        if (true) {
            trySend(Response.Success(true))
        } else {
            trySend(Response.Failure(Exception("Can not commit changes.")))
        }
        awaitClose()
    }
}