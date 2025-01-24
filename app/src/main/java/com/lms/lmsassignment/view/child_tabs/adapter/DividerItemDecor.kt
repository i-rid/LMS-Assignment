package com.lms.lmsassignment.view.child_tabs.adapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lms.lmsassignment.R

class DividerItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val paint: Paint = Paint().apply {
        color = context.getColor(R.color.lms_primary) // Change to desired color
        style = Paint.Style.FILL
    }

    private val dividerHeight = context.resources.displayMetrics.density * 1 // 1dp height

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft.toFloat()
        val right = (parent.width - parent.paddingRight).toFloat()

        for (i in 0 until parent.childCount - 1) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = (child.bottom + params.bottomMargin).toFloat()
            val bottom = top + dividerHeight

            canvas.drawRect(left, top, right, bottom, paint)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.set(0, 0, 0, dividerHeight.toInt())
    }
}