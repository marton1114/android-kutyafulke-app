package com.example.kutyafulke.data.alarms

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.kutyafulke.R
import com.example.kutyafulke.data.models.OwnedDog
import com.example.kutyafulke.data.models.Response
import com.example.kutyafulke.data.repositories.interfaces.OwnedDogsRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlarmBroadcastReceiver: BroadcastReceiver() {
    @Inject
    lateinit var ownedDogsRepository: OwnedDogsRepository

    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("MESSAGE") ?: return
        val id = intent.getStringExtra("ID") ?: return
        val name = intent.getStringExtra("NAME") ?: return

        val notificationManager = context
            ?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(context, "pill")
            .setContentTitle(name)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_launcher_foreground)

        getOwnedDog(id)

        notificationManager.notify(id.hashCode(), notification.build())
    }

    private fun getOwnedDog(id: String) = CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
        ownedDogsRepository.getOwnedDog(id).collect{ response ->
            when (response) {
                is Response.Success -> {
                    updateOwnedDog(response.data.copy(pillGiven = false))
                    this.cancel()
                }
                is Response.Loading -> {}
                is Response.Failure -> {}
            }
        }
    }

    private fun updateOwnedDog(ownedDog: OwnedDog) = CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
        ownedDogsRepository.updateOwnedDog(ownedDog).collect { response ->
            when (response) {
                is Response.Success -> {this.cancel()}
                is Response.Loading -> {}
                is Response.Failure -> {}
            }
        }
    }
}