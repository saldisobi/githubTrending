package com.saldi.gittrending.ui.list

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.DividerItemDecoration
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
                    viewModel.getPosts("", "", "", false)
                }
            })
        }

        swipeRefresh.setOnRefreshListener {
            viewModel.getForcePost()
            val handler = Handler()
            handler.postDelayed(Runnable {
                if (swipeRefresh.isRefreshing) {
                    swipeRefresh.isRefreshing = false
                }
            }, 1000)
        }

        viewModel.trendingLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ApiResponse.ApiLoading -> {
                    trendingRecyclerView.setLoading()
                }
                is ApiResponse.ApiSuccessResponse -> {
                    var trendingAdapter = it.data?.let { it1 ->
                        activity?.baseContext?.let { it2 ->
                            TrendingAdapter(
                                it1,
                                it2
                            )
                        }
                    }
                    setAdapter(trendingAdapter)
                }
                is ApiResponse.ApiErrorResponse -> {
                    trendingRecyclerView.setError()
                }
            }
        })

    }

    private fun setAdapter(trendingAdapter: TrendingAdapter?) {
        trendingRecyclerView.mRecyclerView.addItemDecoration(
            DividerItemDecoration(
                trendingRecyclerView.mRecyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )
        trendingRecyclerView.setAdapter(trendingAdapter)

        val selectionTracker = SelectionTracker.Builder(
            SELECTION,
            trendingRecyclerView.mRecyclerView,
            TrendingAdapter.KeyProvider(),
            TrendingAdapter.DetailsLookup(trendingRecyclerView.mRecyclerView),
            StorageStrategy.createLongStorage()
        )
            .withSelectionPredicate(TrendingAdapter.Predicate())
            .build()


        trendingAdapter?.setSelectionTracker(selectionTracker)

    }

    companion object {
        const val SELECTION = "my_selection"
    }

}
