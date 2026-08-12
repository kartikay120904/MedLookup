package com.kartikay.medlookup.data.remote

import retrofit2.Response

class FakeFdaApi : FdaApi {

    var response: Response<FdaResponse> =
        Response.success(
            FdaResponse(
                meta = null,
                results = emptyList()
            )
        )

    var exception: Exception? = null

    override suspend fun searchDrugs(
        search: String,
        limit: Int,
        skip: Int
    ): Response<FdaResponse> {
        exception?.let { throw it }

        return response
    }
}