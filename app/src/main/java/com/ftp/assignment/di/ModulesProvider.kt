package com.ftp.assignment.di

import com.ftp.assignment.remote.ApiService
import com.ftp.assignment.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ModulesProvider {

    @Singleton
    @Provides
    fun providesRetrofit() : Retrofit {
        val url = "https://jsonplaceholder.typicode.com/"
        return Retrofit.Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesApi(retrofit : Retrofit) : ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun providesRepository(api : ApiService) = Repository(api)

}