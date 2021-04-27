package com.pbsarathy21.trendingrepos.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pbsarathy21.trendingrepos.data.models.Repository
import kotlinx.coroutines.flow.Flow

@Dao
interface RepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repositories: List<Repository>)

    @get:Query("select * from repositories")
    val repositories: Flow<List<Repository>>
}