package com.saldi.gittrending.di

import com.saldi.gittrending.TrendingApplication
import dagger.Component

interface AppComponent {
    @Component.Builder
    interface Builder {
        fun build(): AppComponent
    }

    fun inject(trendingApplication: TrendingApplication)
}