package com.saldi.gittrending.data.repository

import com.saldi.gittrending.data.model.ApiResponse
import com.saldi.gittrending.data.model.ScanParserItem
import com.saldi.gittrending.data.network.ScanService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

open class ScanRepository @Inject constructor(
    private val scanService: ScanService
) {

    open fun getScanData(): Flow<ApiResponse<List<ScanParserItem>>> {
        return object :
            NetworkBoundRepositoryNoPersist<List<ScanParserItem>, List<ScanParserItem>>() {

            override suspend fun fetchFromRemote(): Response<List<ScanParserItem>> =
                scanService.getScanData()
        }.asFlow().flowOn(Dispatchers.IO)
    }
}