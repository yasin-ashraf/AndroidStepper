package com.yasin.materialstepper

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap

/**
 * Created by Yasin on 20/10/19.
 */
class StepperView : View {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){
        initialise(context,attrs,0)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        initialise(context,attrs,defStyleAttr)
    }

    private var currentStep = 0
    private var stepper: Stepper = Stepper()

    private fun initialise(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.stepper_view,
            defStyleAttr,
            0
        )
        val dm = resources.displayMetrics
        val dp100 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100f, dm)
        val dp5 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, dm)
        val dashLength = a.getDimension(R.styleable.stepper_view_dash_size,dp100)
        val circleRadius = a.getDimension(R.styleable.stepper_view_circle_radius,dp5)
        val steps = a.getInt(R.styleable.stepper_view_steps,3)
        val successDrawable = a.getResourceId(R.styleable.stepper_view_success_drawable,R.drawable.ic_success_tick)
        val colorCompleted = a.getColor(R.styleable.stepper_view_color_completed,ContextCompat.getColor(context,R.color.completed))
        val colorUnCompleted = a.getColor(R.styleable.stepper_view_color_un_completed,ContextCompat.getColor(context,R.color.un_completed))
        val equalSpacing = a.getBoolean(R.styleable.stepper_view_equal_spacing,true)
        currentStep = a.getInteger(R.styleable.stepper_view_current_step,0)

        stepper.setSteps(steps)
        stepper.setEqualSpacing(equalSpacing)
        stepper.setDashLength(dashLength)
        stepper.setCircleRadius(circleRadius)
        stepper.setColorCompleted(colorCompleted)
        stepper.setColorUnCompleted(colorUnCompleted)
        stepper.setSuccessDrawable(
            AppCompatResources.getDrawable(context, successDrawable)?.toBitmap()!!
        )
        a.recycle()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        stepper.setBounds(
            width - paddingLeft - paddingRight,
            height - paddingTop - paddingBottom
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val save = canvas?.save()
        canvas?.translate(paddingLeft.toFloat(), paddingTop.toFloat())
        stepper.draw(canvas,currentStep,width-paddingLeft-paddingRight)
        canvas?.restoreToCount(save ?: -1)
    }
}