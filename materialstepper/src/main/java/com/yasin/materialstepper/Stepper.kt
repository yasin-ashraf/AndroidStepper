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
        drawCompletedLine(canvas,currentPosition)
        canvas?.restoreToCount(firstLine ?: -1)
        val emptyLine = canvas?.save()
        drawLine(canvas,currentPosition)
        canvas?.restoreToCount(emptyLine ?: -1)
        drawCircles(canvas,currentPosition)
        drawCompletedCircles(canvas,currentPosition)
    }

    private fun drawCompletedCircles(canvas: Canvas?, currentPosition: Int) {
        for (i in 0 until currentPosition) {
            val drawBitmap = canvas?.save()
            canvas?.drawBitmap(
                successBitmap,
                bounds.left.toFloat() + i*dashLength - circleRadius,
                (bounds.height().toFloat() / 2 ) - circleRadius ,
                paint
            )
            canvas?.restoreToCount(drawBitmap ?: -1)
        }
    }

    private fun drawCompletedLine(canvas: Canvas?, currentPosition: Int) {
        paint.color = colorCompleted
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 4f
        for(i in 0 until currentPosition -1){
            canvas?.drawLine(
                bounds.left.toFloat() + circleRadius + dashLength * i,
                bounds.height().toFloat() / 2,
                bounds.left.toFloat() + (dashLength*(i+1)) - circleRadius,
                bounds.height().toFloat() / 2,
                paint)
        }
    }

    private fun drawLine(canvas: Canvas?, currentPosition: Int) {
        paint.color = colorUncompleted
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 4f
        for(i in currentPosition-1 until steps-1){
            canvas?.drawLine(
                (currentPosition-1) + circleRadius + dashLength*i,
                bounds.height().toFloat() / 2,
                (currentPosition-1) + (dashLength*(i+1)) - circleRadius,
                bounds.height().toFloat() / 2,
                paint
            )

        }
    }

    private fun drawCircles(canvas: Canvas?, currentPosition: Int) {
        paint.color = colorUncompleted
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5f
        paint.alpha = 255
        for (i in currentPosition until steps) {
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