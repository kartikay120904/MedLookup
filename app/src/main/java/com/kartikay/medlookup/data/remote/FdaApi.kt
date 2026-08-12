package com.kartikay.medlookup.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FdaApi {

    @GET("drug/label.json")
    suspend fun searchDrugs(
        @Query("search") search: String,
        @Query("limit") limit: Int = 20,
        @Query("skip") skip: Int = 0
    ): Response<FdaResponse>
}