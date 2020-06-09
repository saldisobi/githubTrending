package com.saldi.customui.recycler

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saldi.customui.R

class StateRecyclerView : RelativeLayout {

    private lateinit var mEmptyView: View
    private lateinit var mErrorView: View
    private lateinit var mLoadingView: View
    private lateinit var mRecyclerView: RecyclerView

    private val stateActionHandler by lazy {
        StateActionHandler()
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        inflate(context, R.layout.state_recycler_view, this)
        mEmptyView = findViewById(R.id.empty_view)
        mErrorView = findViewById(R.id.error_view)
        mLoadingView = findViewById(R.id.loading_view)
        mRecyclerView = findViewById(R.id.content_view)
    }

    fun setEmpty() {
        mRecyclerView.visibility = View.GONE
        mErrorView.visibility = View.GONE
        mLoadingView.visibility = View.GONE
        mEmptyView.visibility = View.VISIBLE
    }

    fun provideErrorView(view: View) {
        mErrorView = view
        addView(mErrorView)
        mErrorView.visibility = View.GONE
    }

    fun provideErrorView(@LayoutRes layout: Int) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mErrorView = inflater.inflate(layout, null, false)
        addView(mErrorView)
        mErrorView.visibility = View.GONE
    }

    fun provideEmptyView(@LayoutRes layout: Int) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mEmptyView = inflater.inflate(layout, null, false)
        mEmptyView.visibility = View.GONE
        addView(mEmptyView)
    }

    fun provideEmptyView(view: View) {
        mEmptyView = view
        addView(mEmptyView)
        mEmptyView.visibility = View.GONE
    }

    fun provideLoadingView(view: View) {
        mLoadingView = view
        addView(mLoadingView)
    }

    fun provideLoadingView(@LayoutRes layout: Int) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mLoadingView = inflater.inflate(layout, null, false)
        addView(mLoadingView)
    }


    fun setAdapter(adapter: RecyclerView.Adapter<*>?) {
        mRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.adapter = adapter
        checkIfEmpty()
    }

    private fun checkIfEmpty() {
        if (mRecyclerView.adapter != null) {
            val emptyViewVisible = mRecyclerView?.adapter?.itemCount == 0
            mRecyclerView.visibility = if (emptyViewVisible) View.GONE else View.VISIBLE
            mEmptyView.visibility = if (emptyViewVisible) View.VISIBLE else View.GONE

            mErrorView.visibility = View.GONE
            mLoadingView.visibility = View.GONE
        }
    }

    fun setLoading() {
        mLoadingView.visibility = View.VISIBLE
        mEmptyView.visibility = View.GONE
        mErrorView.visibility = View.GONE
        mRecyclerView.visibility = View.GONE
    }

    fun setError() {
        mLoadingView.visibility = View.GONE
        mEmptyView.visibility = View.GONE
        mErrorView.visibility = View.VISIBLE
        mRecyclerView.visibility = View.GONE
    }

    override fun onSaveInstanceState(): Parcelable? {
        val bundle = Bundle()
        bundle.putParcelable(STATE_SUPER_CLASS, super.onSaveInstanceState())
        bundle.putInt(EMPTY_VIEW, mEmptyView.visibility)
        bundle.putInt(ERROR_VIEW, mErrorView.visibility)
        bundle.putInt(LOADING_VIEW, mLoadingView.visibility)
        bundle.putParcelable(RECYCLER_LAYOUT, mRecyclerView.layoutManager?.onSaveInstanceState())
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val bundle = state as Bundle
        super.onRestoreInstanceState(bundle.getParcelable(STATE_SUPER_CLASS))
        mEmptyView.visibility =
            if (bundle.getInt(EMPTY_VIEW) == View.VISIBLE) View.VISIBLE else View.GONE
        mErrorView.visibility =
            if (bundle.getInt(ERROR_VIEW) == View.VISIBLE) View.VISIBLE else View.GONE
        mLoadingView.visibility =
            if (bundle.getInt(LOADING_VIEW) == View.VISIBLE) View.VISIBLE else View.GONE
        val savedRecyclerLayoutState = bundle.getParcelable(RECYCLER_LAYOUT) as Parcelable?
        mRecyclerView.layoutManager?.onRestoreInstanceState(savedRecyclerLayoutState)
    }

    fun provideRetryAction(
        @IdRes retryButtonId: Int = 0,
        listener: StateActionHandler.ActionClickListener
    ) {
        stateActionHandler.register(retryButtonId, listener)
        stateActionHandler.enableAction(this)
    }

    companion object {
        const val EMPTY_VIEW = "empty_view"
        const val LOADING_VIEW = "loading_view"
        const val ERROR_VIEW = "error_view"
        const val RECYCLER_LAYOUT = "com.saldi.StateRecyclerView"
        const val STATE_SUPER_CLASS = "SuperClassState"
    }
}

