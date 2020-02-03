package com.myToDoList.utils

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridItemDecoration(private val context: Context, private val spanCount: Int, private val space: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            //super.getItemOffsets(outRect, view, parent, state)
            val pos = parent.getChildAdapterPosition(view)
            val space = Math.round(space * context.resources.displayMetrics.density)
            val column = pos % spanCount
            outRect.left = space - column * space / spanCount
            outRect.right = (column + 1) * space / spanCount
            if (pos < spanCount) {
                outRect.top = space
            }
            outRect.bottom = space
        }
    }