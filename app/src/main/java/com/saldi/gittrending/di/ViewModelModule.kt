package com.saldi.gittrending.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.saldi.gittrending.ui.list.TrendingListViewModel
import com.saldi.gittrending.viewmodel.GithubViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(TrendingListViewModel::class)
    abstract fun provideTrendingViewModel(viewModel: TrendingListViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: GithubViewModelFactory): ViewModelProvider.Factory
}