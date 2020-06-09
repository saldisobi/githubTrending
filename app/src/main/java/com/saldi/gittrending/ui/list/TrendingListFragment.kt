package com.saldi.gittrending.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.saldi.customui.recycler.StateActionHandler
import com.saldi.gittrending.R
import com.saldi.gittrending.data.model.ApiResponse
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.trending_list_fragment.*
import javax.inject.Inject

class TrendingListFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    private val viewModel: TrendingListViewModel by viewModels { viewModelFactory }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.trending_list_fragment, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(trendingRecyclerView) {
            provideEmptyView(R.layout.empty_layout)
            provideErrorView(R.layout.error_layout)
            provideLoadingView(R.layout.loading_layout)
            provideRetryAction(R.id.retry, object : StateActionHandler.ActionClickListener {
                override fun onClick(view: View) {
                    trendingRecyclerView.setLoading()
                }
            })

        }

        viewModel.trendingLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ApiResponse.ApiLoading -> {
                    trendingRecyclerView.setLoading()
                }
                is ApiResponse.ApiSuccessResponse -> {
                    trendingRecyclerView.setAdapter(
                        it.data?.let { it1 ->
                            activity?.baseContext?.let { it2 ->
                                TrendingAdapter(
                                    it1,
                                    it2
                                )
                            }
                        }
                    )

                }
                is ApiResponse.ApiErrorResponse -> {
                    trendingRecyclerView.setError()
                }
            }
        })
    }

    companion object {
        fun newInstance() = TrendingListFragment()
    }

}
