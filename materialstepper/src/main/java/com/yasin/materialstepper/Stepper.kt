package com.yasin.materialstepper

import android.graphics.*
import kotlin.math.min

/**
 * Created by Yasin on 19/10/19.
 */
class Stepper {

    private var maxDisplayedSteps = 10
    private var circleRadius = 10f
    private var dashLength = 50f
    private var steps = 0
    private var equalSpacing = false
    private var bounds = Rect()
    private var paint: Paint = Paint()
    private var colorUncompleted: Int = 0
    private var colorCompleted: Int = 0
    private lateinit var successBitmap : Bitmap

    fun getWidth(items: Int): Int {
        val numberOfItems = min(items, maxDisplayedSteps)
        if (numberOfItems == 0) return 0
        return (numberOfItems * (2 * circleRadius) + (numberOfItems - 1) * dashLength).toInt()
    }

    fun getHeight(): Float {
        return circleRadius * 2
    }

    fun setCircleRadius(radius: Float) {
        this.circleRadius = radius
    }

    fun setDashLength(dashLength: Float) {
        this.dashLength = dashLength
    }

    fun setSteps(steps: Int) {
        this.steps = steps
    }

    fun setColorCompleted(color: Int) {
        this.colorCompleted = color
    }

    fun setColorUnCompleted(color: Int) {
        this.colorUncompleted = color
    }

    fun setBounds(width: Int, height: Int) {
        bounds.set(0, 0, width, height)
    }

    fun setEqualSpacing(equalSpacing: Boolean) {
        this.equalSpacing = equalSpacing
    }

    fun setSuccessDrawable(bitmap: Bitmap) {
        this.successBitmap = bitmap
    }

    fun draw(
        canvas: Canvas?,
        currentPosition: Int,
        width: Int
    ) {
        dashLength = (width / (steps - 1)).toFloat()
        //draw line from start to end
        val firstLine = canvas?.save()
        drawLine(canvas)
        canvas?.restoreToCount(firstLine ?: -1)
        drawCircles(canvas)
        //draw completed Line
        drawCompletedLine(canvas,currentPosition)
        //draw completed circle
        drawCompletedCircles(canvas,currentPosition)
    }

    private fun drawCompletedCircles(canvas: Canvas?, currentPosition: Int) {
        paint.color = colorCompleted
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.strokeWidth = 8f
        for (i in 0..currentPosition) {
            val drawBitmap = canvas?.save()
            canvas?.drawBitmap(
                successBitmap,
                bounds.left.toFloat() + i*dashLength - circleRadius,
                (bounds.height().toFloat() / 2 ) - circleRadius ,
                null
            )
            canvas?.restoreToCount(drawBitmap ?: -1)
        }
    }

    private fun drawCompletedLine(canvas: Canvas?, currentPosition: Int) {
        paint.color = colorCompleted
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 4f
        canvas?.drawLine(
            bounds.left.toFloat(),
            bounds.height().toFloat() / 2,
            bounds.left.toFloat() + dashLength*currentPosition,
            bounds.height().toFloat() / 2,
            paint
        )
    }

    private fun drawLine(canvas: Canvas?) {
        paint.color = colorUncompleted
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 4f
        canvas?.drawLine(
            bounds.left.toFloat(),
            bounds.height().toFloat() / 2,
            bounds.width().toFloat(),
            bounds.height().toFloat() / 2,
            paint
        )
    }

    private fun drawCircles(canvas: Canvas?) {
        paint.color = colorUncompleted
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.strokeWidth = 8f
        for (i in 0 until steps) {
            val drawCircle = canvas?.save()
            canvas?.drawCircle(
                bounds.left.toFloat() + i*dashLength,
                bounds.height().toFloat() / 2,
                circleRadius,
                paint
            )
            canvas?.restoreToCount(drawCircle ?: -1)
        }
    }
}