package com.kartikay.medlookup.data.remote

data class FdaResponse(
    val meta: FdaMeta?,
    val results: List<FdaDrug>
)

data class FdaMeta(
    val results: FdaResultMeta?
)

data class FdaResultMeta(
    val skip: Int?,
    val limit: Int?,
    val total: Int?
)

data class FdaDrug(
    val id: String?,
    val openfda: OpenFda?,
    val purpose: List<String>?,
    val indications_and_usage: List<String>?,
    val dosage_and_administration: List<String>?,
    val warnings: List<String>?,
    val do_not_use: List<String>?,
    val stop_use: List<String>?,
    val active_ingredient: List<String>?,
    val inactive_ingredient: List<String>?,
    val storage_and_handling: List<String>?
)

data class OpenFda(
    val brand_name: List<String>?,
    val generic_name: List<String>?,
    val manufacturer_name: List<String>?,
    val route: List<String>?,
    val product_type: List<String>?
)