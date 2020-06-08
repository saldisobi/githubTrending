package com.saldi.gittrending.di

import android.app.Application
import com.saldi.gittrending.TrendingApplication
import com.saldi.gittrending.di.builder.ActivityBuilder
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class, ActivityBuilder::class, ActivityBuilder::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(trendingApplication: TrendingApplication)
}