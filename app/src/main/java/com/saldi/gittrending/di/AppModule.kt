package com.saldi.gittrending.di

import android.app.Application
import android.content.Context
import com.saldi.gittrending.data.network.ScanService
import com.saldi.gittrending.data.utils.NetworkUtils
import dagger.Module
import dagger.Provides
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofitService(): ScanService {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return Retrofit.Builder()
            .baseUrl(NetworkUtils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ScanService::class.java)
    }

    @Singleton
    @Provides
    fun provideApplicationContext(application: Application): Context {
        return application.baseContext
    }

}