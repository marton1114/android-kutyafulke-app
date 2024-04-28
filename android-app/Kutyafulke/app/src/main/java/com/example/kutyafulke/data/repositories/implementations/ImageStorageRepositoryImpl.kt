package com.example.kutyafulke.data.repositories.implementations

import android.net.Uri
import com.example.kutyafulke.data.models.Response
import com.example.kutyafulke.data.repositories.interfaces.ImageStorageRepository
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.nio.file.Paths
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageStorageRepositoryImpl @Inject constructor(
    private val firebaseStorage: FirebaseStorage
) : ImageStorageRepository {
    override fun uploadImage(image: Uri, userId: String): Flow<Response<Uri>> = callbackFlow {
        firebaseStorage.reference
            .child("owned-dog-images")
            .child(userId)
            .child(Calendar.getInstance().timeInMillis.toString())
            .putFile(image)
            .await()
            .storage
            .downloadUrl
            .addOnSuccessListener { uri ->
                if (uri != null) {
                    trySend(Response.Success(uri))
                }
            }
            .addOnFailureListener { e -> trySend(Response.Failure(e)) }
        awaitClose()
    }

    override fun deleteImage(image: Uri, userId: String): Flow<Response<Boolean>> = callbackFlow {
        firebaseStorage.reference
            .child("owned-dog-images")
            .child(userId)
            .child(Paths.get(image.lastPathSegment).fileName.toString() )
            .delete()
            .addOnSuccessListener { trySend(Response.Success(true)) }
            .addOnFailureListener { e -> trySend(Response.Failure(e)) }
        awaitClose()
    }
}
