package com.example.assiette_pto.utils

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemOffsetDecoration(context: Context, resId: Int) : RecyclerView.ItemDecoration() {
    private val itemOffset: Int = context.resources.getDimensionPixelSize(resId)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.set(itemOffset, itemOffset, itemOffset, itemOffset)
    }
}
