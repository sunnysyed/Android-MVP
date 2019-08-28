package com.sunny.androidmvp.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sunny.androidmvp.App
import com.sunny.androidmvp.R


class SimpleDividerItemDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private val mDivider: Drawable = ContextCompat.getDrawable(context, R.drawable.line_divider)!!

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft + TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15F, App.getContext()?.resources?.displayMetrics)
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider.intrinsicHeight

            mDivider.setBounds(left.toInt(), top, right, bottom)
            mDivider.draw(c)
        }
    }
}