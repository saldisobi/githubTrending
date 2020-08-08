package com.saldi.gittrending.data.network

import com.saldi.gittrending.data.model.ScanParserItem
import retrofit2.Response
import retrofit2.http.GET

interface ScanService {
    @GET("/data")
    suspend fun getScanData(): Response<List<ScanParserItem>>
}