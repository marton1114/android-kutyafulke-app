package com.example.kutyafulke.data.repositories.implementations

import android.annotation.SuppressLint
import android.location.Location
import android.os.Looper
import com.example.kutyafulke.data.models.Response
import com.example.kutyafulke.data.repositories.interfaces.LocationRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton


@SuppressLint("MissingPermission")
@Singleton
class LocationRepositoryImpl @Inject constructor(
    private val providerClient: FusedLocationProviderClient
): LocationRepository {
    override fun requestLocationUpdates(interval: Long): Flow<Response<Location>> = callbackFlow {
        val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, interval).build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                super.onLocationResult(result)
                result.locations.lastOrNull()?.let { location ->
                    launch { send(Response.Success(location)) }
                }
            }
        }

        providerClient.requestLocationUpdates(
            request,
            locationCallback,
            Looper.getMainLooper()
        )

        awaitClose {
            providerClient.removeLocationUpdates(locationCallback)
        }
    }
}