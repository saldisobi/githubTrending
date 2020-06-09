package com.saldi.gittrending.data.repository

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.saldi.gittrending.data.model.ApiResponse
import com.saldi.gittrending.data.utils.NetworkUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.Response

abstract class NetworkBoundRepository<RESULT, REQUEST> {

    fun asFlow() = flow<ApiResponse<RESULT>> {
        try {
            emit(ApiResponse.loading())
            if (checkFetchPreCondition()) {
                onRemoteFetchRequired()
                Log.v("SALDI111", "fetching freom remote")
                val apiResponse = fetchFromRemote()

                val remoteResponse = apiResponse.body()

                if (apiResponse.isSuccessful && remoteResponse != null) {
                    // Save posts into the persistence storage
                    saveRemoteData(remoteResponse)
                } else {
                    // Something went wrong! Emit Error state.
                    emit(ApiResponse.error(apiResponse.message()))
                }
            } else {
                Log.v("SALDI111", "NOT fetching freom remote")
            }
        } catch (exception: Exception) {
            emit(ApiResponse.error(NetworkUtils.ERROR_MESSAGE))
            exception.printStackTrace()
        }

        emitAll(fetchFromLocal().map {
            ApiResponse.success<RESULT>(it)
        })
    }

    /**
     * Fetches [Response] from the remote end point.
     */
    @MainThread
    protected abstract suspend fun fetchFromRemote(): Response<REQUEST>

    /**
     * Saves retrieved from remote into the persistence storage.
     */
    @WorkerThread
    protected abstract suspend fun saveRemoteData(response: REQUEST)

    /**
     * Retrieves all data from persistence storage.
     */
    @MainThread
    protected abstract fun fetchFromLocal(): Flow<RESULT>

    /**
     * Checks if we need to fetch data
     */
    @MainThread
    protected abstract fun checkFetchPreCondition(): Boolean

    /**
     * Checks if we need to fetch data
     */
    @MainThread
    protected abstract fun onRemoteFetchRequired()
}