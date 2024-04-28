package com.example.kutyafulke.di

import android.content.Context
import com.example.kutyafulke.data.repositories.implementations.AuthenticationRepositoryImpl
import com.example.kutyafulke.data.repositories.implementations.BreedsRepositoryImpl
import com.example.kutyafulke.data.repositories.implementations.ImageStorageRepositoryImpl
import com.example.kutyafulke.data.repositories.implementations.OwnedDogPreferencesRepositoryImpl
import com.example.kutyafulke.data.repositories.implementations.OwnedDogsRepositoryImpl
import com.example.kutyafulke.data.repositories.implementations.SurveysRepositoryImpl
import com.example.kutyafulke.data.repositories.interfaces.AuthenticationRepository
import com.example.kutyafulke.data.repositories.interfaces.BreedsRepository
import com.example.kutyafulke.data.repositories.interfaces.ImageStorageRepository
import com.example.kutyafulke.data.repositories.interfaces.OwnedDogPreferencesRepository
import com.example.kutyafulke.data.repositories.interfaces.OwnedDogsRepository
import com.example.kutyafulke.data.repositories.interfaces.SurveysRepository
import com.example.kutyafulke.domain.use_cases.AddOwnedDogUseCase
import com.example.kutyafulke.domain.use_cases.AddOwnedDogUseCaseImpl
import com.example.kutyafulke.domain.use_cases.DeleteOwnedDogUseCase
import com.example.kutyafulke.domain.use_cases.DeleteOwnedDogUseCaseImpl
import com.example.kutyafulke.domain.use_cases.GetStatisticsUseCase
import com.example.kutyafulke.domain.use_cases.GetStatisticsUseCaseImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object BusinessLogicModule {
    @Provides
    fun providePreferencesRepository(
        @ApplicationContext context: Context
    ): OwnedDogPreferencesRepository = OwnedDogPreferencesRepositoryImpl(
        context = context
    )

    @Provides
    fun provideAuthenticationRepository(
        firebaseAuth: FirebaseAuth
    ): AuthenticationRepository = AuthenticationRepositoryImpl(
        firebaseAuth = firebaseAuth
    )

    @Provides
    fun provideImageStorageRepository(
        firebaseStorage: FirebaseStorage
    ) : ImageStorageRepository = ImageStorageRepositoryImpl(
        firebaseStorage = firebaseStorage
    )

    @Provides
    fun provideBreedsRepository(
        @Named("breeds-collection") breedsCollectionReference: CollectionReference
    ): BreedsRepository = BreedsRepositoryImpl(
        breedsCollectionReference =  breedsCollectionReference
    )

    @Provides
    fun provideOwnedDogsRepository(
        @Named("owned-dogs-collection") ownedDogsCollectionReference: CollectionReference
    ): OwnedDogsRepository = OwnedDogsRepositoryImpl(
        ownedDogsCollectionReference
    )

    @Provides
    fun provideSurveysRepository(
        @Named("surveys-collection") surveysCollectionReference: CollectionReference
    ): SurveysRepository = SurveysRepositoryImpl(
        surveysCollectionReference
    )

    @Provides
    fun provideGetStatisticsUseCase(
        surveysRepository: SurveysRepository
    ): GetStatisticsUseCase = GetStatisticsUseCaseImpl(
        surveysRepository
    )

    @Provides
    fun provideAddOwnedDogUseCase(
        authenticationRepository: AuthenticationRepository,
        breedsRepository: BreedsRepository,
        getStatisticsUseCase: GetStatisticsUseCase,
        imageStorageRepository: ImageStorageRepository,
        ownedDogsRepository: OwnedDogsRepository
    ): AddOwnedDogUseCase = AddOwnedDogUseCaseImpl(
        authenticationRepository = authenticationRepository,
        breedsRepository = breedsRepository,
        getStatisticsUseCase = getStatisticsUseCase,
        imageStorageRepository = imageStorageRepository,
        ownedDogsRepository = ownedDogsRepository
    )

    @Provides
    fun provideDeleteDogUseCase(
        authenticationRepository: AuthenticationRepository,
        imageStorageRepository: ImageStorageRepository,
        ownedDogsRepository: OwnedDogsRepository
    ): DeleteOwnedDogUseCase = DeleteOwnedDogUseCaseImpl(
        authenticationRepository = authenticationRepository,
        ownedDogsRepository = ownedDogsRepository,
        imageStorageRepository = imageStorageRepository
    )
}