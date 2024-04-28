package com.example.kutyafulke.di

import com.example.kutyafulke.data.repositories.implementations.LocationRepositoryImpl
import com.example.kutyafulke.data.repositories.interfaces.LocationRepository
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent

@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {
    // Repository providers
    @Provides
    fun provideLocationRepository(
        fusedLocationProviderClient: FusedLocationProviderClient
    ): LocationRepository = LocationRepositoryImpl(
        providerClient = fusedLocationProviderClient,
    )
}
