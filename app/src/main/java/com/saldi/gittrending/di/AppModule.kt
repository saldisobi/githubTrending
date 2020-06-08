package com.saldi.gittrending.di

import android.app.Application
import com.saldi.gittrending.data.db.TrendingDatabase
import com.saldi.gittrending.data.network.GitHubService
import com.saldi.gittrending.data.utils.NetworkUtils
import dagger.Module
import dagger.Provides
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
    fun provideRetrofitService(): GitHubService = Retrofit.Builder()
        .baseUrl(NetworkUtils.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GitHubService::class.java)

}