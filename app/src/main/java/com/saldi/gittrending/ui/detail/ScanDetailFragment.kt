package com.saldi.gittrending.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.saldi.gittrending.MainActivity
import com.saldi.gittrending.R
import com.saldi.gittrending.data.model.ScanParserItem
import com.saldi.gittrending.ui.list.ScanListViewModel
import com.saldi.gittrending.utils.parseItem
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.trending_detail_fragment.*
import javax.inject.Inject


class ScanDetailFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    private val viewModel by viewModels<ScanListViewModel>({ activity as MainActivity }) { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.trending_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.scanItem.observe(viewLifecycleOwner, Observer {
            populateView(it)
        })
    }

    private fun populateView(scanParserItem: ScanParserItem?) {
        var scanParsedItem = activity?.baseContext?.let { scanParserItem?.parseItem(it) }
        name.text = scanParsedItem?.name
        scanTag.text = scanParsedItem?.tag
        scanParsedItem?.color?.let { scanTag.setTextColor(it) }


        criteriaRecyclerView.layoutManager = LinearLayoutManager(context)
        var adapter = scanParsedItem?.criteria?.let { CriteriaAdapter(it) }
        criteriaRecyclerView.adapter = adapter


    }


    companion object {
        private const val POSITION = "position"

        @JvmStatic
        fun newInstance(position: Int) = ScanDetailFragment().apply {
            arguments = Bundle().apply {
                putInt(POSITION, position)
            }
        }

    }

}
