package com.saldi.gittrending.di.builder

import com.saldi.gittrending.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [MainActivityFragmentBuilder::class])
    abstract fun contributeMainActivity(): MainActivity
}