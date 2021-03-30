package com.example.notekeeperkotlin.customViews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.example.notekeeperkotlin.R

/**
 * TODO: document your custom view class.
 */
class ColorDialView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var colors : ArrayList<Int> = arrayListOf(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW,
    Color.RED, Color.DKGRAY, Color.BLACK, Color.CYAN)

    private var dialDrawable: Drawable? = null
    private var dialDiameter = toDP(100)

    //pre-computed helper values
    private var horizontalSize = 0f
    private var verticalSize = 0f

    //pre-computed position values
    private var tickPositionVertical = 0f
    private var centerHorizontal = 0f
    private var centerVertical = 0f

    private val extraPadding : Int = toDP(30)
    private var tickSize  = toDP(10).toFloat()
    private val angleBetweenColors = 0f

    //pre-computed padding values
    private var totalLeftPadding = 0f
    private var totalRightPadding = 0f
    private var totalBottomPadding = 0f
    private var totalTopPadding = 0f

    init {
        dialDrawable = context.getDrawable(R.drawable.ic_dial).also {
            it?.bounds = getCenteredBounds(dialDiameter)
            it?.setTint(Color.DKGRAY)

            refreshValues()
    }

    }

    private fun refreshValues(){
        //compute padding values
        this.totalLeftPadding = (paddingLeft + extraPadding).toFloat()
        this.totalRightPadding = (paddingRight + extraPadding).toFloat()
        this.totalTopPadding = (paddingTop + extraPadding).toFloat()
        this.totalBottomPadding = (paddingBottom + extraPadding).toFloat()

        //compute helper values
        this.horizontalSize = dialDiameter.toFloat()
        this.verticalSize = dialDiameter.toFloat()

        //compute position values
        this.tickPositionVertical = (paddingTop + extraPadding) / 2f
        this.centerHorizontal = horizontalSize / 2f
        this.centerVertical = verticalSize / 2f
    }

    private fun getCenteredBounds(size: Int, scalar: Float = 1f): Rect {
        val half = ((if (size >= 0) size / 2 else 1)* scalar).toInt()
        return Rect(-half, -half, half, half)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.translate(centerHorizontal, centerVertical)
        dialDrawable?.draw(canvas)

    }

    private fun toDP(value: Int): Int{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
        value.toFloat(),
        context.resources.displayMetrics).toInt()
    }
}