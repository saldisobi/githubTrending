package com.saldi.gittrending.ui.list

import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saldi.gittrending.data.model.TrendingListItem
import com.saldi.gittrending.databinding.ItemTrendingBinding


class TrendingAdapter(val trendingList: List<TrendingListItem>, val context: Context) :
    RecyclerView.Adapter<TrendingAdapter.TrendingListViewHolder>() {
    private var selectionTracker: SelectionTracker<Long>? = null

    fun setSelectionTracker(
        selectionTracker: SelectionTracker<Long>
    ) {
        this.selectionTracker = selectionTracker
    }

    class Details : ItemDetailsLookup.ItemDetails<Long>() {

        var position: Long = 0

        override fun getPosition(): Int {
            return position.toInt()
        }

        @Nullable
        override fun getSelectionKey(): Long? {
            return position
        }

        override fun inSelectionHotspot(@NonNull e: MotionEvent): Boolean {
            return true
        }
    }

    internal class KeyProvider : ItemKeyProvider<Long>(ItemKeyProvider.SCOPE_MAPPED) {

        @Nullable
        override fun getKey(position: Int): Long? {
            return position.toLong()
        }

        override fun getPosition(@NonNull key: Long): Int {
            return key.toInt()
        }
    }

    internal class DetailsLookup(private val recyclerView: RecyclerView) :
        ItemDetailsLookup<Long>() {

        @Nullable
        override fun getItemDetails(@NonNull e: MotionEvent): ItemDetailsLookup.ItemDetails<Long>? {
            val view = recyclerView.findChildViewUnder(e.x, e.y)
            if (view != null) {
                val viewHolder = recyclerView.getChildViewHolder(view)
                if (viewHolder is TrendingListViewHolder) {
                    return viewHolder.mItemDetails
                }
            }
            return null
        }
    }

    internal class Predicate : SelectionTracker.SelectionPredicate<Long>() {

        override fun canSetStateForKey(@NonNull key: Long, nextState: Boolean): Boolean {
            return true
        }

        override fun canSetStateAtPosition(position: Int, nextState: Boolean): Boolean {
            return true
        }

        override fun canSelectMultiple(): Boolean {
            return true
        }
    }


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


    inner class TrendingListViewHolder(@param:NonNull var itemTrendingBinding: ItemTrendingBinding) :
        RecyclerView.ViewHolder(itemTrendingBinding.root) {

        val mItemDetails: Details = Details()

        fun bind(trendingListItem: TrendingListItem) {
            mItemDetails.position = position.toLong()
            itemTrendingBinding.repo.text = trendingListItem.name
            itemTrendingBinding.developer.text = trendingListItem.author
            Glide.with(itemTrendingBinding.imageView.context)
                .load(trendingListItem.avatar)
                .into(itemTrendingBinding.imageView);

            if (selectionTracker != null) {
                if (this@TrendingAdapter.selectionTracker?.isSelected(mItemDetails.selectionKey) == true) {
                    itemTrendingBinding.expandGroup.visibility = View.VISIBLE
                    itemTrendingBinding.textView5.text = trendingListItem.language
                    itemTrendingBinding.textView3.text = trendingListItem.description
                    itemTrendingBinding.textView6.text = trendingListItem.forks.toString()
                    itemTrendingBinding.textView7.text = trendingListItem.stars.toString()
                } else {
                    itemTrendingBinding.expandGroup.visibility = View.GONE
                }
            }
        }
    }
}