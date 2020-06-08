package com.saldi.gittrending.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.saldi.gittrending.R
import com.saldi.gittrending.data.model.ApiResponse
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class TrendingListFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    private val viewModel: TrendingListViewModel by viewModels { viewModelFactory }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.trending_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.trendingLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ApiResponse.ApiLoading -> {
                    Log.v("saldi111", "showloader")
                }
                is ApiResponse.ApiSuccessResponse -> {
                    Log.v("saldi111", it.data?.size.toString())

                }
                is ApiResponse.ApiErrorResponse -> {
                    Log.v("saldi111", "hide loader error")
                }
            }
        })
    }

    companion object {
        fun newInstance() = TrendingListFragment()
    }

}
