package com.example.zayatspexelsapp.di

import android.app.Application
import androidx.room.Room
import com.example.zayatspexelsapp.common.Constants.BASE_URL
import com.example.zayatspexelsapp.data.database.AppDatabase
import com.example.zayatspexelsapp.data.network.ApiService
import com.example.zayatspexelsapp.data.repositories.DataRepository
import com.example.zayatspexelsapp.data.repositories.DatabaseRepository
import com.example.zayatspexelsapp.data.repositories.implementations.AppDatabaseRepository
import com.example.zayatspexelsapp.data.repositories.implementations.PexelsDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DependenciesProvider {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePexelsDataRepository(apiService: ApiService): DataRepository {
        return PexelsDataRepository(apiService)
    }

    @Provides
    @Singleton
    fun provideDatabaseRepository(db: AppDatabase): DatabaseRepository {
        return AppDatabaseRepository(db)
    }

    @Provides
    @Singleton
    fun provideFavouritesDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "favourites.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}