package com.saldi.gittrending.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = TrendingListItem.TABLE_NAME)
data class TrendingListItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = 0,
    val author: String,
    val avatar: String,
    val builtBy: List<BuiltBy>,
    val currentPeriodStars: Int,
    val description: String,
    val forks: Int,
    val language: String,
    val languageColor: String,
    val name: String,
    val stars: Int,
    val url: String
) {
    companion object {
        const val TABLE_NAME = "trending"
    }
}