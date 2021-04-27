package com.pbsarathy21.trendingrepos.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pbsarathy21.trendingrepos.data.daos.RepositoryDao
import com.pbsarathy21.trendingrepos.data.models.Repository

@Database(entities = [Repository::class], version = 1)
abstract class TrendingRepoDatabase : RoomDatabase() {
    
    abstract fun repositoryDao(): RepositoryDao
    
    companion object {
        private var INSTANCE: TrendingRepoDatabase? = null
        private const val DB_NAME = "trending_repos.db"

        fun getDatabase(context: Context): TrendingRepoDatabase {
            synchronized(TrendingRepoDatabase::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        TrendingRepoDatabase::class.java,
                        DB_NAME
                    ).fallbackToDestructiveMigration()
                        .build()
                }
            }

            return INSTANCE!!
        }
    }
}