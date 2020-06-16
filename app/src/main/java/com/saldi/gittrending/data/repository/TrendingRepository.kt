package com.saldi.gittrending.data.repository

import com.saldi.gittrending.data.db.TinyDB
import com.saldi.gittrending.data.db.TrendingDao
import com.saldi.gittrending.data.model.ApiResponse
import com.saldi.gittrending.data.model.TrendingListItem
import com.saldi.gittrending.data.network.GitHubService
import com.saldi.gittrending.data.utils.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

open class TrendingRepository @Inject constructor(
    private val gitHubService: GitHubService,
    private val trendingDao: TrendingDao,
    private val tintDb: TinyDB
) {

  open  fun getTrending(
        language: String,
        since: String,
        spokenLanguage: String,
        isForce: Boolean
    ): Flow<ApiResponse<List<TrendingListItem>>> {
        return object : NetworkBoundRepository<List<TrendingListItem>, List<TrendingListItem>>() {

            override suspend fun saveRemoteData(response: List<TrendingListItem>) {
                tintDb.putLong(NetworkUtils.PREVIOUS_SYNC_TIME, NetworkUtils.getCurrentTime())
                trendingDao.insertTrending(response)

            }

            override fun fetchFromLocal(): Flow<List<TrendingListItem>> {

                return trendingDao.getAllTrending()

            }

            override suspend fun fetchFromRemote(): Response<List<TrendingListItem>> =
                gitHubService.getTrendingRepositories(language, since, spokenLanguage)

            override fun checkFetchPreCondition(): Boolean {
                return isForce || isUpdateRequired()
            }

            override fun onRemoteFetchRequired() {
                //Deliberately not checking condition of clearing when we do first time as clearing from  empty table should not be much I/O intensive task
                trendingDao.deleteAllTrending()
            }

        }.asFlow().flowOn(Dispatchers.IO)
    }

    fun isUpdateRequired(): Boolean {
        return NetworkUtils.isUpdateRequired(tintDb.getLong(NetworkUtils.PREVIOUS_SYNC_TIME, 0))
    }
}