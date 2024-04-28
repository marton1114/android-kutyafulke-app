package com.example.kutyafulke.data.repositories.interfaces

import android.net.Uri
import com.example.kutyafulke.data.models.Response
import kotlinx.coroutines.flow.Flow

interface ImageStorageRepository {
    fun uploadImage(image: Uri, userId: String): Flow<Response<Uri>>
    fun deleteImage(image: Uri, userId: String): Flow<Response<Boolean>>
}
