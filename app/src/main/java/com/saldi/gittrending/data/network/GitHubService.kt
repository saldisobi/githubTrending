package com.saldi.gittrending.data.network

import com.saldi.gittrending.data.model.TrendingListItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubService {

    @GET("/repositories")
    suspend fun getTrendingRepositories(
        @Query("language") language: String,
        @Query("since") since: String,
        @Query("spoken_language_code") spokenLanguageCode: String
    ): Response<List<TrendingListItem>>
}