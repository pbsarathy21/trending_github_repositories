package com.pbsarathy21.trendingrepos.network

import com.pbsarathy21.trendingrepos.data.models.Repository
import retrofit2.Response
import retrofit2.http.GET

interface APIService {

    @GET("repositories")
    suspend fun getTrendingRepositories(): Response<List<Repository>>
}