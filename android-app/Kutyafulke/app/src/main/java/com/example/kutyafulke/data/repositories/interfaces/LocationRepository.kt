package com.example.kutyafulke.data.repositories.interfaces

import android.location.Location
import com.example.kutyafulke.data.models.Response
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun requestLocationUpdates(interval: Long): Flow<Response<Location>>
}