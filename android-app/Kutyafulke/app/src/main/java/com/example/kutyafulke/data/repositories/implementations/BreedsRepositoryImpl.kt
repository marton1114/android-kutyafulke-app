package com.example.kutyafulke.data.repositories.implementations

import com.example.kutyafulke.data.models.Breed
import com.example.kutyafulke.data.models.Response
import com.example.kutyafulke.data.repositories.interfaces.BreedsRepository
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class BreedsRepositoryImpl @Inject constructor (
    @Named("breeds") private val breedsCollectionReference: CollectionReference
): BreedsRepository {
    override fun getBreeds(): Flow<Response<List<Breed>>> = callbackFlow {
        breedsCollectionReference
            .addSnapshotListener{ value, error ->
                if (value != null) {
                    val breeds = value.toObjects(Breed::class.java)
                    trySend(Response.Success(breeds))
                } else {
                    trySend(Response.Failure(Exception(error?.message)))
                }
            }
        awaitClose()
    }

    override fun getBreed(id: String): Flow<Response<Breed>> = callbackFlow {
        breedsCollectionReference
            .whereEqualTo("id", id)
            .addSnapshotListener { value, error ->
                if (value != null) {
                    val breeds = value.toObjects(Breed::class.java)
                    trySend(Response.Success(breeds[0]))
                } else {
                    trySend(Response.Failure(Exception(error?.message)))
                }
            }
        awaitClose()
    }
}

