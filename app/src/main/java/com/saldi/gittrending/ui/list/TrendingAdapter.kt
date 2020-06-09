package com.saldi.gittrending.ui.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saldi.gittrending.data.model.TrendingListItem
import com.saldi.gittrending.databinding.ItemTrendingBinding


class TrendingListViewHolder(val itemTrendingBinding: ItemTrendingBinding) :
    RecyclerView.ViewHolder(itemTrendingBinding.root) {
    fun bind(trendingListItem: TrendingListItem) {
        itemTrendingBinding.repoName.text = trendingListItem.name
        itemTrendingBinding.devName.text = trendingListItem.author
        Glide.with(itemTrendingBinding.imageView.context)
            .load(trendingListItem.avatar)
            .into(itemTrendingBinding.imageView);
    }


}

class TrendingAdapter(val trendingList: List<TrendingListItem>, val context: Context) :
    RecyclerView.Adapter<TrendingListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingListViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding: ItemTrendingBinding =
            ItemTrendingBinding.inflate(layoutInflater, parent, false)
        return TrendingListViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return trendingList.size
    }

    override fun onBindViewHolder(holder: TrendingListViewHolder, position: Int) {
        var trendingListItem = trendingList[position];
        holder.bind(trendingListItem);
    }
}