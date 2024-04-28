package com.example.kutyafulke.data.repositories.implementations


import com.example.kutyafulke.data.models.OwnedDog
import com.example.kutyafulke.data.models.Response
import com.example.kutyafulke.data.models.Response.Failure
import com.example.kutyafulke.data.models.Response.Success
import com.example.kutyafulke.data.repositories.interfaces.OwnedDogsRepository
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OwnedDogsRepositoryImpl @Inject constructor (
    private val ownedDogsCollectionReference: CollectionReference
): OwnedDogsRepository {
    override fun getOwnedDog(id: String): Flow<Response<OwnedDog>> = callbackFlow {
        ownedDogsCollectionReference
            .whereEqualTo("id", id)
            .addSnapshotListener { snapshot, e ->
                trySend(
                    if (snapshot != null) {
                        if (snapshot.isEmpty) {
                            Failure(Exception("Collection is empty."))
                        } else {
                            Success(snapshot.first().toObject(OwnedDog::class.java))
                        }
                    } else {
                        Failure(e as Exception)
                    }
                )
            }
        awaitClose()
    }

    override fun getOwnedDogsByUserId(userId: String): Flow<Response<List<OwnedDog>>> = callbackFlow {
        ownedDogsCollectionReference
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snapshot, e ->
                trySend(
                    if (snapshot != null) {
                        Success(snapshot.toObjects(OwnedDog::class.java))
                    } else {
                        Failure(e as Exception)
                    }
                )
            }
        awaitClose()
    }

    override fun addOwnedDog(ownedDog: OwnedDog): Flow<Response<Boolean>> = callbackFlow {
        val documentId = ownedDogsCollectionReference.document().id

        val ownedDogToSend = ownedDog.copy(id = documentId)

        ownedDogsCollectionReference
            .document(documentId)
            .set(ownedDogToSend)
            .addOnSuccessListener {
                trySend(Success(true))
            }
            .addOnFailureListener { e ->
                trySend(Failure(e))
            }
        awaitClose()
    }

    override fun updateOwnedDog(ownedDog: OwnedDog): Flow<Response<Boolean>> = callbackFlow {
        val updatedDataMap = hashMapOf<String, Any>(
            "creationTime" to ownedDog.creationTime,
            "verified" to ownedDog.verified,
            "dogName" to ownedDog.dogName,
            "dogWeight" to ownedDog.dogWeight,
            "walkingComponentEnabled" to ownedDog.walkingComponentEnabled,
            "healthComponentEnabled" to ownedDog.healthComponentEnabled,


            "lastWalkingResetTime" to ownedDog.lastWalkingResetTime,
            "locked" to ownedDog.locked,
            "traveledWeeklyDistance" to ownedDog.traveledWeeklyDistance,
            "neededWeeklyDistance" to ownedDog.neededWeeklyDistance,

            "pillGiven" to ownedDog.pillGiven,
            "nextPillDateInMillis" to ownedDog.nextPillDateInMillis,
            "neededPills" to ownedDog.neededPills,
        )

        ownedDogsCollectionReference
            .document(ownedDog.id)
            .update(updatedDataMap)
            .addOnSuccessListener {
                trySend(Success(true))
            }
            .addOnFailureListener { e ->
                trySend(Failure(e))
            }
        awaitClose()
    }

    override fun deleteOwnedDogById(ownedDogId: String): Flow<Response<Boolean>> = callbackFlow {
        ownedDogsCollectionReference
            .document(ownedDogId)
            .delete()
            .addOnSuccessListener {
                trySend(Success(true))
            }
            .addOnFailureListener {e ->
                trySend(Failure(e))
            }
        awaitClose()
    }
}