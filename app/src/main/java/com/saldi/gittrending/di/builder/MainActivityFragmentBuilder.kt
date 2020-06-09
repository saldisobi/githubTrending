package com.saldi.gittrending.di.builder

import com.saldi.gittrending.ui.list.TrendingListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class MainActivityFragmentBuilder {
    @ContributesAndroidInjector
    abstract fun contributeRepoFragment(): TrendingListFragment
}