package com.pbsarathy21.trendingrepos.data.repos

import com.pbsarathy21.trendingrepos.network.APIService
import com.pbsarathy21.trendingrepos.network.SafeApiRequest
import javax.inject.Inject

class TrendingRepository @Inject constructor(private val apiService: APIService) : SafeApiRequest() {
}