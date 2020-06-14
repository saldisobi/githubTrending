package com.saldi.gittrending.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saldi.gittrending.data.model.ApiResponse
import com.saldi.gittrending.data.model.TrendingListItem
import com.saldi.gittrending.data.repository.TrendingRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class TrendingListViewModel @Inject constructor(private val trendingRepository: TrendingRepository) :
    ViewModel() {
    private val _trendingLiveData = MutableLiveData<ApiResponse<List<TrendingListItem>>>()

    val trendingLiveData: LiveData<ApiResponse<List<TrendingListItem>>>
        get() = _trendingLiveData

    init {
        //TODO remove unused form here
        getPosts("", "daily", "", false)
    }

    fun getPosts(language: String, since: String, spokenLanguage: String, isforce: Boolean) {
        viewModelScope.launch {
            trendingRepository.getTrending(language, since, spokenLanguage,isforce).collect {
                _trendingLiveData.value = it
            }
        }
    }

    fun getForcePost() {
        getPosts("", "daily", "", true)
    }
}
