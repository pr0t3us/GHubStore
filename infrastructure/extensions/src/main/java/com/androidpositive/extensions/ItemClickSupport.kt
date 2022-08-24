package com.androidpositive.extensions

import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnLongClickListener
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnChildAttachStateChangeListener

class ItemClickSupport private constructor(private val recyclerView: RecyclerView) {
    private var onItemClickListener: OnItemClickListener? = null
    private var onItemLongClickListener: OnItemLongClickListener? = null
    private val onClickListener: OnClickListener = OnClickListener { v ->
        if (onItemClickListener != null) {
            val holder: RecyclerView.ViewHolder = recyclerView.getChildViewHolder(v)
            onItemClickListener!!.onItemClicked(recyclerView, holder.absoluteAdapterPosition, v)
        }
    }
    private val onLongClickListener: OnLongClickListener = object : OnLongClickListener {
        override fun onLongClick(v: View): Boolean {
            if (onItemLongClickListener != null) {
                val holder: RecyclerView.ViewHolder = recyclerView.getChildViewHolder(v)
                return onItemLongClickListener!!.onItemLongClicked(
                    recyclerView, holder.absoluteAdapterPosition, v
                )
            }
            return false
        }
    }
    private val onChildAttachStateChangeListener: OnChildAttachStateChangeListener =
        object : OnChildAttachStateChangeListener {
            override fun onChildViewAttachedToWindow(view: View) {
                if (onItemClickListener != null) {
                    view.setOnClickListener(onClickListener)
                }
                if (onItemLongClickListener != null) {
                    view.setOnLongClickListener(onLongClickListener)
                }
            }

            override fun onChildViewDetachedFromWindow(view: View) {
            }
        }

    fun setOnItemClickListener(listener: OnItemClickListener?): ItemClickSupport {
        onItemClickListener = listener
        return this
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener?): ItemClickSupport {
        onItemLongClickListener = listener
        return this
    }

    private fun detach(view: RecyclerView) {
        view.removeOnChildAttachStateChangeListener(onChildAttachStateChangeListener)
        view.setTag(R.id.item_click_support, null)
    }

    interface OnItemClickListener {
        fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View)
    }

    interface OnItemLongClickListener {
        fun onItemLongClicked(recyclerView: RecyclerView, position: Int, v: View): Boolean
    }

    companion object {
        fun addTo(view: RecyclerView): ItemClickSupport {
            var support = view.getTag(R.id.item_click_support) as? ItemClickSupport?
            if (support == null) {
                support = ItemClickSupport(view)
            }
            return support
        }

        fun removeFrom(view: RecyclerView): ItemClickSupport? {
            val support = view.getTag(R.id.item_click_support) as? ItemClickSupport?
            support?.detach(view)
            return support
        }

        fun RecyclerView.setOnItemClickListener(listener: OnItemClickListener?): ItemClickSupport {
            var support = getTag(R.id.item_click_support) as? ItemClickSupport?
            if (support == null) {
                support = ItemClickSupport(this)
            }
            support.setOnItemClickListener(listener)
            return support
        }
    }

    init {
        recyclerView.setTag(R.id.item_click_support, this)
        recyclerView.addOnChildAttachStateChangeListener(onChildAttachStateChangeListener)
    }
}
