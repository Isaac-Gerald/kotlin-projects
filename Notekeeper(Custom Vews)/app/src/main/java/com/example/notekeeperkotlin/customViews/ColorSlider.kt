package com.example.notekeeperkotlin.customViews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.core.content.ContextCompat
import com.example.notekeeperkotlin.R

class ColorSlider @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.attr.seekBarStyle
) : AppCompatSeekBar(context, attrs, defStyleAttr) {

    private var colors: ArrayList<Int> = arrayListOf(Color.RED, Color.YELLOW, Color.BLUE)

   private val w = getPixelValueFromDp(16f)
   private val h = getPixelValueFromDp(16f)
   private val halfW = if (w >= 0) w / 2f else 1f
   private val halfH = if (h >= 0) h / 2f else 1f
   private val paint = Paint()
    private var noColorDrawable: Drawable? = null
    set(value) {
        val w2 = value?.intrinsicWidth ?: 0
        val h2 = value?.intrinsicHeight ?: 0

        val halfW2 = if (w2 >= 0) w2 / 2 else 1
        val halfH2 = if (h2 >= 0) h2 / 2 else 1
        value?.setBounds(-halfW2, -halfH2, halfW2, halfH2)
        field = value
    }
    var w2 = 0
    var h2 = 0
    var halfW2 = 1
    var halfH2 = 1

    init {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorSlider)
         noColorDrawable = context.getDrawable(R.drawable.ic_clear_red_24)

        try {
            colors = typedArray.getTextArray(R.styleable.ColorSlider_colors)
                .map {
                    Color.parseColor(it.toString())
                } as ArrayList<Int>

        } finally {
            typedArray.recycle()
        }

        colors.add(0, android.R.color.transparent)

        max = colors.size - 1

        progressBackgroundTintList = ContextCompat.getColorStateList(
            context,
            android.R.color.transparent
        )

        progressTintList = ContextCompat.getColorStateList(context, android.R.color.transparent)
        splitTrack = false

        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom + getPixelValueFromDp(16f).toInt())
        thumb = context.getDrawable(R.drawable.ic_arrow_thumb)

        setOnSeekBarChangeListener(object : OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                listeners.forEach{
                    it(colors[progress])
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })


    }

    var selectedColorValue: Int = android.R.color.transparent
    set(value) {
        var index = colors.indexOf(value)
        progress = if (index == -1){
            0
        }else{
            index
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawTickMarks(canvas)
    }

    private val listeners: ArrayList<(Int) -> Unit> = arrayListOf()

    fun addListener(function : (Int) -> Unit){
        listeners.add(function)
    }

    private fun drawTickMarks(canvas: Canvas?) {
        canvas?.let {
            val count = colors.size
            val saveCount = canvas.save()
            canvas.translate(paddingLeft.toFloat(), (height / 2).toFloat() + getPixelValueFromDp(16f))

            val spacing = (width - paddingLeft - paddingRight) / (count - 1).toFloat()

            if (count > 1) {
                for (i in 0 until count) {
                    if (i == 0) {
                        noColorDrawable?.draw(canvas)

                    } else {
                        paint.color = colors[i]
                        canvas.drawRect(-halfW, -halfH, halfW, halfH, paint)
                    }

                    canvas.translate(spacing, 0f)
                }
                canvas.restoreToCount(saveCount)
            }



        }
    }

    private fun getPixelValueFromDp(value: Float): Float{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
        value,
        context.resources.displayMetrics)
    }

}
