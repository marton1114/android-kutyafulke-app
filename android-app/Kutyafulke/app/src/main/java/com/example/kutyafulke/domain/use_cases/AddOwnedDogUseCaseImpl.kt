package com.example.kutyafulke.domain.use_cases

import android.net.Uri
import androidx.core.net.toUri
import com.example.kutyafulke.data.models.OwnedDog
import com.example.kutyafulke.data.models.Response
import com.example.kutyafulke.data.repositories.interfaces.AuthenticationRepository
import com.example.kutyafulke.data.repositories.interfaces.BreedsRepository
import com.example.kutyafulke.data.repositories.interfaces.ImageStorageRepository
import com.example.kutyafulke.data.repositories.interfaces.OwnedDogsRepository
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AddOwnedDogUseCaseImpl @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val breedsRepository: BreedsRepository,
    private val getStatisticsUseCase: GetStatisticsUseCase,
    private val imageStorageRepository: ImageStorageRepository,
    private val ownedDogsRepository: OwnedDogsRepository
): AddOwnedDogUseCase {
    private fun getOptimalDogWeight(size: Double): Double {
        val maxValue = 5.0
        val maxDogWeight = 50.0 // (kg) A legnehezebb kutya súlya
        return size * maxDogWeight / maxValue
    }

    private fun getOptimalWeeklyDistance(walking: Double): Double {
        val maxValue = 5.0
        val maxWeeklyDistance = 12000.0 // (km) A legtöbb sétát igénylő kutya mennyisége
        return walking * maxWeeklyDistance / maxValue
    }

    private fun addDog(uri: Uri, breedId: String) = callbackFlow {
        getStatisticsUseCase.invoke(breedId).collect { response ->
            when (response) {
                is Response.Success -> {
                    val ownedDog = OwnedDog(
                        imageUri = uri.toString(),
                        breedId = breedId,
                        userId = authenticationRepository.getCurrentUserId(),
                        averageDogWeight = getOptimalDogWeight(response.data.size)
                            .toString(),
                        neededWeeklyDistance =
                            getOptimalWeeklyDistance(response.data.walking).toFloat(),
                    )
                    ownedDogsRepository.addOwnedDog(ownedDog).collect {
                        when (it) {
                            is Response.Success -> {
                                trySend(Response.Success(it.data))
                            }
                            is Response.Failure -> {
                                trySend(Response.Failure(it.e))
                            }
                            else -> {}
                        }
                    }
                }
                is Response.Failure -> {Response.Failure(response.e)}
                else -> {}
            }
        }
    }

    override fun invoke(uri: Uri, breedId: String) = callbackFlow {
        var imageUri = Uri.EMPTY
        if (uri != Uri.EMPTY) {
            imageStorageRepository.uploadImage(uri,
                authenticationRepository.getCurrentUserId()).collect { response ->
                when (response) {
                    is Response.Success -> {
                        imageUri = response.data

                        addDog(imageUri, breedId).collect {
                            when (it) {
                                is Response.Success -> { trySend(Response.Success(it.data)) }
                                is Response.Failure -> { trySend(Response.Failure(it.e)) }
                                else -> {}
                            }
                        }
                    }
                    is Response.Failure -> { trySend(Response.Failure(response.e)) }
                    else -> {}
                }
            }
        } else {
            breedsRepository.getBreed(breedId).collect { response ->
                when (response) {
                    is Response.Success -> {
                        imageUri = response.data.imageUri.toUri()

                        addDog(imageUri, breedId).collect {
                            when (it) {
                                is Response.Success -> { trySend(Response.Success(it.data)) }
                                is Response.Failure -> { trySend(Response.Failure(it.e)) }
                                else -> {}
                            }
                        }
                    }
                    is Response.Failure -> { trySend(Response.Failure(response.e)) }
                    else -> {}
                }
            }
        }
    }
}