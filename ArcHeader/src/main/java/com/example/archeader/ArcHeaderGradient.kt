package com.example.archeader

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class ArcHeaderGradient @JvmOverloads constructor(
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
    var headerColorStart = getDefaultHeaderGradientStartColor()
        set(value) {
            field = value

            resetPaintGradientParameters()
            invalidate()


        }
    var headerColorEnd = getDefaultHeaderGradientEndColor()
        set(value) {
            field = value

            resetPaintGradientParameters()
            invalidate()
        }

    init {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.ArcHeaderGradient,
            0,
            0)
        arcHeight = typedArray.getDimension(R.styleable.ArcHeaderGradient_arc_height, 0f)
        headerColorStart = typedArray.getColor(R.styleable.ArcHeaderGradient_header_color_start, getDefaultHeaderGradientStartColor())
        headerColorEnd = typedArray.getColor(R.styleable.ArcHeaderGradient_header_color_end, getDefaultHeaderGradientEndColor())
        typedArray.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        resetPathDimensions(h, w)
        resetPaintGradientParameters(h, w)

        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawPath(path, paint)
    }

    private fun resetPaintGradientParameters(height: Int = getHeight(), width: Int = getWidth()) {
        paint.shader = LinearGradient(
            0f,
            height.toFloat(),
            width.toFloat(),
            0f,
            headerColorStart,
            headerColorEnd,
            Shader.TileMode.MIRROR
        )
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

    private fun getDefaultHeaderGradientStartColor() = ContextCompat.getColor(context, R.color.header_color_gradient_start_default)

    private fun getDefaultHeaderGradientEndColor() = ContextCompat.getColor(context, R.color.header_color_gradient_end_default)
}
