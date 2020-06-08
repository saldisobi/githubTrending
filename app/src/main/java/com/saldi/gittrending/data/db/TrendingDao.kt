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


    @Query("DELETE FROM ${DbUtils.TABLE_TRENDING_LIST}")
    fun deleteAllTrending()


    @Query("SELECT * FROM ${DbUtils.TABLE_TRENDING_LIST}")
    fun getAllTrending(): Flow<List<TrendingListItem>>
}