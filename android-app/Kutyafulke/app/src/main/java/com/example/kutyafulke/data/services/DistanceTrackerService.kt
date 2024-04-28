package com.example.kutyafulke.data.services

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.kutyafulke.R
import com.example.kutyafulke.data.models.Response
import com.example.kutyafulke.data.repositories.interfaces.LocationRepository
import com.example.kutyafulke.data.repositories.interfaces.OwnedDogPreferencesRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@SuppressLint("MissingPermission")
@AndroidEntryPoint
class DistanceTrackerService: Service() {
    @Inject
    lateinit var locationRepository: LocationRepository
    @Inject
    lateinit var ownedDogPreferencesRepository: OwnedDogPreferencesRepository

    companion object {
        private lateinit var notificationManager: NotificationManager
        private var scope: CoroutineScope? = null
        private var idSet = setOf<String>()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val id = intent?.getStringExtra("ID")
        when (intent?.action) {
            Action.UNLOCK.action -> { id?.let { enable(it) } }
            Action.LOCK.action -> { id?.let { disable(it) } }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun enable(id: String) {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        idSet = idSet.plus(id)
        clearOwnedDogPreferences(id)

        val notificationBuilder = NotificationCompat.Builder(this, "location")
            .setContentTitle(getString(R.string.notification_title_label)).setContentText(
                getString(
                    R.string.walking_notification_content_label
                ))
            .setSmallIcon(R.drawable.ic_launcher_foreground).setOngoing(true)

        if (scope == null) {
            scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

            locationRepository
                .requestLocationUpdates(1000L)
                .onEach { response ->
                    if (response is Response.Success) {
                        idSet.forEach {
                            addToOwnedDogDistancePreferences(it, response.data)
                        }
                    }
                }
                .launchIn(scope!!)
        }
        startForeground(id.hashCode(), notificationBuilder.build())
    }

    private fun disable(id: String) {
        idSet = idSet.minus(id)
        notificationManager.cancel(id.hashCode())

        if (idSet.isEmpty()) {
            stopSelf()
            scope?.cancel()
            scope = null
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    enum class Action(val action: String) {
        UNLOCK("UNLOCK"),
        LOCK("LOCK")
    }

    private fun clearOwnedDogPreferences(ownedDogId: String) = CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
        ownedDogPreferencesRepository.clear(ownedDogId).collect {}
    }

    private fun addToOwnedDogDistancePreferences(ownedDogId: String, location: Location) = CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
        lateinit var lastLocation: Location
        var distance = 0.0F
        ownedDogPreferencesRepository.getLastLocation(ownedDogId).collect { lastLocResponse ->
            when (lastLocResponse) {
                is Response.Success -> { lastLocation = lastLocResponse.data}
                is Response.Failure -> { lastLocation = location }
                is Response.Loading -> { }
            }
            ownedDogPreferencesRepository.getDistance(ownedDogId).collect { distResponse ->
                when (distResponse) {
                    is Response.Success -> { distance = distResponse.data}
                    is Response.Failure -> { distance = 0.0F}
                    is Response.Loading -> { }
                }
                val displacementPerSecond = location.distanceTo(lastLocation)
                ownedDogPreferencesRepository.setDistance(
                    ownedDogId = ownedDogId,
                    distance =
                        if (displacementPerSecond <= 4.0) {
                            distance + displacementPerSecond
                        } else {
                            distance
                        }
                ).collect {
                    ownedDogPreferencesRepository.setLastLocation(ownedDogId, location).collect {}
                }
            }
        }
    }
}