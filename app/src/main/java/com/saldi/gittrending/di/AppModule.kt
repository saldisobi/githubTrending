package com.saldi.gittrending.di

import android.app.Application
import android.content.Context
import com.saldi.gittrending.data.db.TrendingDatabase
import com.saldi.gittrending.data.network.GitHubService
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
    fun provideDatabase(application: Application) = TrendingDatabase.getInstance(application)

    @Singleton
    @Provides
    fun providePostsDao(database: TrendingDatabase) = database.getTrendingDao()

    @Singleton
    @Provides
    fun provideRetrofitService(): GitHubService {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return Retrofit.Builder()
            .baseUrl(NetworkUtils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubService::class.java)
    }

    @Singleton
    @Provides
    fun provideApplicationContext(application: Application): Context {
        return application.baseContext
    }

}