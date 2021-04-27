package com.pbsarathy21.trendingrepos.data.repos

import com.pbsarathy21.trendingrepos.data.daos.RepositoryDao
import com.pbsarathy21.trendingrepos.network.APIService
import com.pbsarathy21.trendingrepos.network.SafeApiRequest
import javax.inject.Inject

class TrendingRepository @Inject constructor(
    private val apiService: APIService,
    private val repositoryDao: RepositoryDao
) : SafeApiRequest() {

    val repositories = repositoryDao.repositories

    suspend fun getTrendingRepositories() {
        val repositories = apiRequest { apiService.getTrendingRepositories() }
        repositoryDao.insert(repositories)
    }
}