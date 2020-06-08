package com.saldi.gittrending.data.repository

import com.saldi.gittrending.data.db.TrendingDao
import com.saldi.gittrending.data.model.ApiResponse
import com.saldi.gittrending.data.model.TrendingListItem
import com.saldi.gittrending.data.network.GitHubService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class TrendingRepository @Inject constructor(
    private val gitHubService: GitHubService,
    private val trendingDao: TrendingDao
) {

    fun getTrending(
        language: String,
        since: String,
        spokenLanguage: String
    ): Flow<ApiResponse<List<TrendingListItem>>> {
        return object : NetworkBoundRepository<List<TrendingListItem>, List<TrendingListItem>>() {

            override suspend fun saveRemoteData(response: List<TrendingListItem>) {
                trendingDao.insertTrending(response)

            }

            override fun fetchFromLocal(): Flow<List<TrendingListItem>> {

                return trendingDao.getAllTrending()

            }

            override suspend fun fetchFromRemote(): Response<List<TrendingListItem>> =
                gitHubService.getTrendingRepositories(language, since, spokenLanguage)

        }.asFlow().flowOn(Dispatchers.IO)
    }
}