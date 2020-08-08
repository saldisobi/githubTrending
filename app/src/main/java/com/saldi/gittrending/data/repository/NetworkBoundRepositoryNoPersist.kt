package com.saldi.gittrending.data.repository

import androidx.annotation.MainThread
import com.saldi.gittrending.data.model.ApiResponse
import com.saldi.gittrending.data.utils.NetworkUtils
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import retrofit2.Response

abstract class NetworkBoundRepositoryNoPersist<RESULT, REQUEST> {

    fun asFlow() = flow<ApiResponse<RESULT>> {
        try {
            emit(ApiResponse.loading())


            val apiResponse = fetchFromRemote()

            val remoteResponse = apiResponse.body()

            if (apiResponse.isSuccessful && remoteResponse != null) {
                emitAll(flowOf(remoteResponse).map {
                    ApiResponse.success(it)
                })
            } else {
                // Something went wrong! Emit Error state.
                emit(ApiResponse.error(apiResponse.message()))
            }


        } catch (exception: Exception) {
            emit(ApiResponse.error(NetworkUtils.ERROR_MESSAGE))
            exception.printStackTrace()
        }


    }

    /**
     * Fetches [Response] from the remote end point.
     */
    @MainThread
    protected abstract suspend fun fetchFromRemote(): Response<RESULT>
}