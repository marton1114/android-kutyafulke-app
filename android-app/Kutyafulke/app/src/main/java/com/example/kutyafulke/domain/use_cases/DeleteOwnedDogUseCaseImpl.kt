package com.example.kutyafulke.domain.use_cases

import androidx.core.net.toUri
import com.example.kutyafulke.data.models.Response
import com.example.kutyafulke.data.repositories.interfaces.AuthenticationRepository
import com.example.kutyafulke.data.repositories.interfaces.ImageStorageRepository
import com.example.kutyafulke.data.repositories.interfaces.OwnedDogsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.nio.file.Paths
import javax.inject.Inject

class DeleteOwnedDogUseCaseImpl @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val ownedDogsRepository: OwnedDogsRepository,
    private val imageStorageRepository: ImageStorageRepository
): DeleteOwnedDogUseCase {
    override fun invoke(ownedDogId: String): Flow<Response<Boolean>> = callbackFlow {
        ownedDogsRepository.getOwnedDog(ownedDogId).collect { ownedDogResponse ->
            when (ownedDogResponse) {
                is Response.Success -> {
                    if (Paths.get(ownedDogResponse.data.imageUri.toUri().lastPathSegment)
                        .parent.toString() == "breeds") {
                        ownedDogsRepository.deleteOwnedDogById(ownedDogId).collect {
                            when (it) {
                                is Response.Success -> { trySend(Response.Success(it.data)) }
                                is Response.Failure -> { trySend(Response.Failure(it.e)) }
                                else -> {}
                            }
                        }
                    } else {
                        imageStorageRepository.deleteImage(ownedDogResponse.data.imageUri.toUri(),
                            authenticationRepository.getCurrentUserId())
                            .collect { imageStorageResponse ->

                                when (imageStorageResponse) {
                                    is Response.Success -> {
                                        ownedDogsRepository.deleteOwnedDogById(ownedDogId).collect {
                                            when (it) {
                                                is Response.Success -> { trySend(Response.Success(it.data)) }
                                                // BUG: Különleges esetben a kép törlődik, de az OwnedDog nem
                                                is Response.Failure -> { trySend(Response.Failure(it.e)) }
                                                else -> {}
                                            }
                                        }
                                    }
                                    is Response.Failure -> { trySend(Response.Failure(imageStorageResponse.e)) }
                                    else -> {}
                                }
                            }
                    }
                }
                is Response.Failure -> { trySend(Response.Failure(ownedDogResponse.e)) }
                else -> {}
            }
        }
    }
}