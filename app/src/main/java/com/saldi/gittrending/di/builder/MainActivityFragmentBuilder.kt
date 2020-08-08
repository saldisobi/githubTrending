package com.saldi.gittrending.di.builder

import com.saldi.gittrending.ui.detail.ScanDetailFragment
import com.saldi.gittrending.ui.list.ScanListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class MainActivityFragmentBuilder {
    @ContributesAndroidInjector
    abstract fun contributeRepoFragment(): ScanListFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailFragment(): ScanDetailFragment
}