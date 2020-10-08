package com.iabhishek1041.archeader

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
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

            resetPathDimensions()
            invalidate()
        }
    var headerColor = getDefaultHeaderColor()
        set(value) {
            field = value

            paint.color = value
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
        resetPathDimensions(h, w)

        paint.color = headerColor

        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawPath(path, paint)
    }

    private fun resetPathDimensions(height: Int = getHeight(), width: Int = getWidth()) {
        path.reset()
        path.lineTo(0f, (height - (arcHeight / 2)))
        path.rCubicTo(
            0f,
            0f,
            (width.toFloat() / 2),
            arcHeight,
            width.toFloat(),
            0f
        )
        path.lineTo(width.toFloat(), 0f)
        path.close()
    }

    private fun getDefaultHeaderColor() = ContextCompat.getColor(context, R.color.header_color_default)
}
