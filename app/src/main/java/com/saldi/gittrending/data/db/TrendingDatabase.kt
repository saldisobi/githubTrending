package com.saldi.gittrending.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.saldi.gittrending.data.model.TrendingListItem

@Database(
    entities = [TrendingListItem::class],
    version = DbUtils.DATABASE_VERSION
)
abstract class TrendingDatabase : RoomDatabase() {

    abstract fun getTrendingDao(): TrendingDao

    companion object {

        @Volatile
        private var INSTANCE: TrendingDatabase? = null

        fun getInstance(context: Context): TrendingDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TrendingDatabase::class.java,
                    DbUtils.DATABASE_NAME
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}