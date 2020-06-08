package com.saldi.gittrending.data.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class TrendingDatabase : RoomDatabase() {

    abstract fun getTrendingDao(): TrendingDao

    companion object {
        const val DB_NAME = "trending_database"

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
                    DB_NAME
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}