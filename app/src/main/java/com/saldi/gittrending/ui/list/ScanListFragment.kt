package com.saldi.gittrending.ui.list

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.saldi.customui.recycler.StateActionHandler
import com.saldi.gittrending.MainActivity
import com.saldi.gittrending.R
import com.saldi.gittrending.data.model.ApiResponse
import com.saldi.gittrending.data.model.ScanParserItem
import com.saldi.gittrending.engine.ScanClickListener
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.trending_list_fragment.*
import javax.inject.Inject


class ScanListFragment : DaggerFragment(), ScanClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    private val viewModel by viewModels<ScanListViewModel>({ activity as MainActivity }) { viewModelFactory }

    private var scanList: List<ScanParserItem>? = null

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
                    viewModel.getScanItems()
                }
            })
        }

        swipeRefresh.setOnRefreshListener {
            viewModel.getScanItems()
            val handler = Handler()
            handler.postDelayed(Runnable {
                if (swipeRefresh.isRefreshing) {
                    swipeRefresh.isRefreshing = false
                }
            }, 1000)
        }

        viewModel.scanLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ApiResponse.ApiLoading<*> -> {
                    trendingRecyclerView.setLoading()
                }
                is ApiResponse.ApiSuccessResponse<*> -> {
                    scanList = it.data
                    var trendingAdapter = it.data?.let { it1 ->
                        activity?.baseContext?.let { it2 ->
                            ScanAdapter(
                                it1,
                                it2,
                                this
                            )
                        }
                    }
                    setAdapter(trendingAdapter)
                }
                is ApiResponse.ApiErrorResponse<*> -> {
                    trendingRecyclerView.setError()
                }
            }
        })

    }

    private fun setAdapter(trendingAdapter: ScanAdapter?) {
        trendingRecyclerView.mRecyclerView.addItemDecoration(
            DividerItemDecoration(
                trendingRecyclerView.mRecyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )
        trendingRecyclerView.setAdapter(trendingAdapter)

    }

    override fun onItemClickListener(position: Int) {
        scanList?.get(position)?.let {
            viewModel.setScanItem(it)
            view?.findNavController()
                ?.navigate(R.id.action_trendingListFragment_to_scanDetailFragment)
        }

    }

}
