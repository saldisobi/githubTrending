package com.saldi.gittrending

import android.app.Activity
import android.app.Application
import com.saldi.gittrending.di.AppInjector
import dagger.android.*
import javax.inject.Inject

class TrendingApplication : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector
}