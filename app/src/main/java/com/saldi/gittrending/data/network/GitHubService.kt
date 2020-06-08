package com.saldi.gittrending.data.network

import com.saldi.gittrending.data.model.TrendingListItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {

    @GET("/repositories")
    suspend fun getTrendingRepositories(
        @Path("language") language: String,
        @Path("since") since: String,
        @Path("spoken_language_code") spokenLanguageCode: String
    ): Response<List<TrendingListItem>>
}