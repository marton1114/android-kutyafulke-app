package com.example.kutyafulke.di

import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    fun provideFusedLocationProvider(
        @ApplicationContext appContext: Context
    ) = LocationServices.getFusedLocationProviderClient(appContext)

    // Firebase Data Source Singletons
    @Provides
    @Singleton
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    @Singleton
    fun provideFirebaseStorage() = Firebase.storage

    @Provides
    @Singleton
    fun provideFirebaseFirestore() = Firebase.firestore

    // Firestore Collection Providers
    @Provides
    @Singleton
    @Named("breeds-collection")
    fun provideBreedsCollectionReference(
        firebaseFirestore: FirebaseFirestore
    ) = firebaseFirestore.collection("breeds")

    @Provides
    @Singleton
    @Named("owned-dogs-collection")
    fun provideOwnedDogsCollectionReference(
        firebaseFirestore: FirebaseFirestore
    ) = firebaseFirestore.collection("owned-dogs")


    @Provides
    @Singleton
    @Named("surveys-collection")
    fun provideSurveysCollectionReference(
        firebaseFirestore: FirebaseFirestore
    ) = firebaseFirestore.collection("surveys")
}