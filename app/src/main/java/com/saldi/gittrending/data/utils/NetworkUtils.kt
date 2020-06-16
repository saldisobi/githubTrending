package com.saldi.gittrending.data.utils

open class NetworkUtils {
    companion object {
        //TODO move this to something like endpoints.gradle for debug and prod url to be configured
        const val BASE_URL = "https://ghapi.huchen.dev/"

        const val ERROR_MESSAGE = "Something went wrong"

        const val PREVIOUS_SYNC_TIME = "previous_sync_time"

        private const val TWO_HOURS = 2 * 60 * 60 * 1000.toLong()

        fun isUpdateRequired(previousFetchTime: Long): Boolean {
            if ((System.currentTimeMillis() - previousFetchTime) > TWO_HOURS) {
                return true
            }
            return false
        }

        fun getCurrentTime(): Long {
            return System.currentTimeMillis()
        }
    }
}