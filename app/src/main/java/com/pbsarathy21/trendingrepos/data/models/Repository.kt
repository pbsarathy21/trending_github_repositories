package com.pbsarathy21.trendingrepos.data.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "repositories", indices = [Index(value = ["url"], unique = true)])
data class Repository(
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    val author: String,
    val name: String,
    val avatar: String,
    val url: String,
    val description: String,
    val language: String,
    val languageColor: String,
    val stars: Long,
    val forks: Long,
    val currentPeriodStars: Long,
)
