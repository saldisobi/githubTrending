package com.saldi.customui.recycler

import android.util.SparseArray
import android.view.View

class StateActionHandler {
    private val clickActionArray: SparseArray<ActionClickListener> by lazy {
        SparseArray<ActionClickListener>()
    }

    fun register(id: Int, listener: ActionClickListener) {
        clickActionArray.append(id, listener)
    }

    fun enableAction(rootView: View) {
        for (i in 0 until clickActionArray.size()) {
            val key = clickActionArray.keyAt(i)
            val view = rootView.findViewById<View>(key)
            view?.setOnClickListener { clickActionArray.get(key).onClick(view) }
        }
    }

    interface ActionClickListener {
        fun onClick(view: View)
    }
}