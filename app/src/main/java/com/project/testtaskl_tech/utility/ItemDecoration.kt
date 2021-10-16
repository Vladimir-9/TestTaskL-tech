package com.project.testtaskl_tech.utility

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val dimens = 10.convertPixelFromDp(parent.context)
        with(outRect) {
            left = dimens
            right = dimens
            top = dimens
        }
    }
}
