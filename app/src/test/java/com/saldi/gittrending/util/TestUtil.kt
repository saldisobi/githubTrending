package com.saldi.gittrending.util

import com.saldi.gittrending.data.model.BuiltBy
import com.saldi.gittrending.data.model.TrendingListItem


object TestUtil {

    fun createTrendingResponse(): ArrayList<TrendingListItem> {
        val builtByList = arrayListOf<BuiltBy>()
        builtByList.add(BuiltBy("", "", "saldi"))

        val trendingListItem =
            TrendingListItem(0, "", "", builtByList, 0, "", 0, "", "", "", 0, "")
        val trendingList = arrayListOf<TrendingListItem>()
        trendingList.add(trendingListItem)

        return trendingList
    }
}
