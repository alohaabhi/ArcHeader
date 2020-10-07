package com.example.archeader

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class ArcHeader @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var path = Path()
    private var paint = Paint()
    var arcHeight = 0f
        set(value) {
            field = value
            invalidate()
        }
    var headerColor = getDefaultHeaderColor()
        set(value) {
            field = value
            invalidate()
        }

    init {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.ArcHeader,
            0,
            0)
        arcHeight = typedArray.getDimension(R.styleable.ArcHeader_arc_height, 0f)
        headerColor = typedArray.getColor(R.styleable.ArcHeader_header_color, getDefaultHeaderColor())
        typedArray.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        path.reset()
        path.lineTo(0f, (h - (arcHeight / 2)))
        path.rCubicTo(
            0f,
            0f,
            (w.toFloat() / 2),
            arcHeight,
            w.toFloat(),
            0f
        )
        path.lineTo(width.toFloat(), 0f)
        path.close()

        paint.color = headerColor

        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawPath(path, paint)
    }

    private fun getDefaultHeaderColor() = ContextCompat.getColor(context, R.color.header_color_default)
}
