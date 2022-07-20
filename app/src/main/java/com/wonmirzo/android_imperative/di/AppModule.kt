package com.wonmirzo.android_imperative.di

import com.wonmirzo.android_imperative.network.Server.IS_TESTER
import com.wonmirzo.android_imperative.network.Server.SERVER_DEVELOPMENT
import com.wonmirzo.android_imperative.network.Server.SERVER_PRODUCTION
import com.wonmirzo.android_imperative.network.TVShowService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    /*
    *  Retrofit Related
    * */
    @Provides
    fun server(): String {
        if (IS_TESTER) return SERVER_DEVELOPMENT
        return SERVER_PRODUCTION
    }

    @Provides
    @Singleton
    fun retrofitClient(): Retrofit {
        return Retrofit.Builder().baseUrl(server())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun tvShowService(): TVShowService {
        return retrofitClient().create(TVShowService::class.java)
    }


    /*
    *  Room Related
    * */
}