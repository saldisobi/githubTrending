package com.saldi.gittrending.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saldi.gittrending.data.model.TrendingListItem
import kotlinx.coroutines.flow.Flow


@Dao
interface TrendingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrending(posts: List<TrendingListItem>)


    @Query("DELETE FROM ${TrendingListItem.TABLE_NAME}")
    fun deleteAllTrending()


    @Query("SELECT * FROM ${TrendingListItem.TABLE_NAME}")
    fun getAllTrending(): Flow<List<TrendingListItem>>
}