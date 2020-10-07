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

    private data class GradientPoints(val x0: Float, val y0: Float, val x1: Float, val y1: Float)

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
    var gradientDirection = GradientDirection.TOP_TO_BOTTOM
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
        val gradientDirectionInt = typedArray.getInt(R.styleable.ArcHeaderGradient_header_gradient_direction, 0)
        gradientDirection = GradientDirection.values()[gradientDirectionInt]
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
        val gradientPoints = getGradientPoints(height, width)
        paint.shader = LinearGradient(
            gradientPoints.x0,
            gradientPoints.y0,
            gradientPoints.x1,
            gradientPoints.y1,
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

    private fun getGradientPoints(height: Int, width: Int): GradientPoints {
        return when (gradientDirection) {
            GradientDirection.TOP_TO_BOTTOM -> GradientPoints(width.toFloat() / 2, 0f, width.toFloat() / 2, height.toFloat())
            GradientDirection.TOP_LEFT_TO_BOTTOM_RIGHT -> GradientPoints(0f, 0f, width.toFloat(), height.toFloat())
            GradientDirection.LEFT_TO_RIGHT -> GradientPoints(0f, height.toFloat() / 2, width.toFloat(), height.toFloat() / 2)
            GradientDirection.BOTTOM_LEFT_TO_TOP_RIGHT -> GradientPoints(0f, height.toFloat(), width.toFloat(), 0f)
        }
    }

    companion object {
        enum class GradientDirection {
            TOP_TO_BOTTOM,
            TOP_LEFT_TO_BOTTOM_RIGHT,
            LEFT_TO_RIGHT,
            BOTTOM_LEFT_TO_TOP_RIGHT
        }
    }
}
