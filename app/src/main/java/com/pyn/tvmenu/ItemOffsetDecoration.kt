package com.pyn.tvmenu

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


class ItemOffsetDecoration(private val space: Int) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.left = 0
        outRect.right = space
        outRect.bottom = 0
        // Add top margin only for the first item to avoid double space between items
        outRect.top = 0
        }
}