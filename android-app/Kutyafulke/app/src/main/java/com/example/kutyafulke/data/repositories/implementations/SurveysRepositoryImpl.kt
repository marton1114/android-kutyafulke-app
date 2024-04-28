package com.example.kutyafulke.data.repositories.implementations

import com.example.kutyafulke.data.models.Response
import com.example.kutyafulke.data.models.Survey
import com.example.kutyafulke.data.repositories.interfaces.SurveysRepository
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class SurveysRepositoryImpl @Inject constructor (
    private val surveysCollectionReference: CollectionReference
):SurveysRepository {
    override fun getSurveysByBreedId(breedId: String): Flow<Response<List<Survey>>> = callbackFlow {
        surveysCollectionReference
            .whereEqualTo("breedId", breedId)
            .get()
            .addOnSuccessListener {
                it.query.addSnapshotListener { value, error ->
                    if (value != null) {
                        val surveys = value.toObjects(Survey::class.java)
                        trySend(Response.Success(surveys))
                    } else {
                        trySend(Response.Failure(Exception(error)))
                    }
                }
            }
        awaitClose()
    }
}