package com.saldi.gittrending.ui.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.saldi.gittrending.data.model.ScanParserItem
import com.saldi.gittrending.databinding.ItemScanBinding
import com.saldi.gittrending.engine.ScanClickListener
import com.saldi.gittrending.utils.ScanUtils


class ScanAdapter(private val scanList: List<ScanParserItem>, val context: Context,private val scanClickListener: ScanClickListener) :
    RecyclerView.Adapter<ScanAdapter.ScanListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScanListViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding: ItemScanBinding =
            ItemScanBinding.inflate(layoutInflater, parent, false)
        return ScanListViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return scanList.size
    }

    override fun onBindViewHolder(holder: ScanListViewHolder, position: Int) {
        holder.itemTrendingBinding.parent.setOnClickListener { scanClickListener.onItemClickListener(position) }
        var trendingListItem = scanList[position];
        holder.bind(trendingListItem);
    }


    inner class ScanListViewHolder(@param:NonNull var itemTrendingBinding: ItemScanBinding) :
        RecyclerView.ViewHolder(itemTrendingBinding.root) {

        fun bind(scanItem: ScanParserItem) {
            itemTrendingBinding.name.text = scanItem.name
            itemTrendingBinding.scanTag.text = scanItem.tag
            itemTrendingBinding.scanTag.setTextColor(
                ScanUtils.getTagColor(
                    scanItem.color,
                    itemTrendingBinding.scanTag.context
                )
            )
        }
    }
}