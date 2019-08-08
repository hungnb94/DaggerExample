package com.example.daggerex

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout

class ExpandableLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var isCollapsed: Boolean = false
        set(value) {
            if (field != value) {
                field = value
                requestLayout()
            }
        }

    var minHeight: Int
        private set
//    var maxHeight: Int
//        private set

    private val density = resources.displayMetrics.density

    private var childId = -1
    private var childView: View? = null

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableLayout, defStyleAttr, 0)

        isCollapsed = typedArray.getBoolean(R.styleable.ExpandableLayout_isCollapsed, false)

        minHeight = typedArray.getDimensionPixelSize(
                R.styleable.ExpandableLayout_minHeight,
                (100 * density).toInt()
        )

        childId = typedArray.getResourceId(R.styleable.ExpandableLayout_opacityView, -1)

        typedArray.recycle()

    }

    companion object {
        private const val TAG = "HHExpandableLayout"
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (isCollapsed) {
            val width = MeasureSpec.getSize(widthMeasureSpec)
            Log.e(TAG, "onMeasure: isCollapsed = $isCollapsed, height = $minHeight, opacity = $childView")
            childView?.visibility = View.VISIBLE
            setMeasuredDimension(width, minHeight)
        } else {
            childView?.visibility = View.GONE
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    override fun onViewAdded(child: View?) {
        super.onViewAdded(child)
        if (child?.id == childId) childView = child
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.e(TAG, "onLayout: isCollapsed = $isCollapsed, opacity = $childView")
        Log.e(TAG, "onLayout: minHeight = $minHeight vs height = $bottom")
        Log.e(TAG, "onLayout: minHeight = $minHeight vs height = $height")
        if (isCollapsed) {
            childView?.let {
                it.layout(left, top, right, minHeight)
            }
        }
    }

//    private val paint = Paint().apply {
//        color = Color.GREEN
//        textSize = 20 * density
//        textAlign = Paint.Align.CENTER
//        style = Paint.Style.STROKE
//    }

//    private val bounds = Rect()
//    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)
//        Log.e(TAG, "onDraw: isCollapsed = $isCollapsed, drawable = $collapsedBackground")
//
//        if (isCollapsed) {
//            collapsedBackground?.draw(canvas)
//            val text = "Show more"
//            paint.getTextBounds(text, 0, text.length, bounds)
//            canvas.drawText(text, width / 2f, height - bounds.height().toFloat(), paint)
//            val left = (width - bounds.width()) / 2f
//            val top = height - 2 * bounds.height().toFloat()
//            val right = (width + bounds.width()) / 2f
//            val bottom = height.toFloat() - bounds.height().toFloat()
//            canvas.drawRect(left, top, right, bottom, paint)
//        } else {
//            val text = "Show less"
//            paint.getTextBounds(text, 0, text.length, bounds)
//            canvas.drawText(text, width / 2f, height - bounds.height().toFloat(), paint)
//            val left = (width - bounds.width()) / 2f
//            val top = height - 2 * bounds.height().toFloat()
//            val right = (width + bounds.width()) / 2f
//            val bottom = height.toFloat() - bounds.height().toFloat()
//            canvas.drawRect(left, top, right, bottom, paint)
//        }
//    }
}