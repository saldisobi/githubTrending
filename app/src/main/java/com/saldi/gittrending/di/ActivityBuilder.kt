package com.saldi.gittrending.di

import com.saldi.gittrending.MainActivity
import dagger.android.ContributesAndroidInjector

abstract class ActivityBuilder {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}