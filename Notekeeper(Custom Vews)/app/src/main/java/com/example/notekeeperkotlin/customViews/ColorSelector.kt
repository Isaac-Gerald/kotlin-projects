package com.example.notekeeperkotlin.customViews

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.notekeeperkotlin.R
import kotlinx.android.synthetic.main.color_selector.view.*

class ColorSelector @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
   private var listOfColors = listOf(Color.BLUE, Color.RED, Color.GREEN)
    private var selectedColorIndex = 0
    init {
        orientation = HORIZONTAL

//        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorSelector)
//        listOfColors = typedArray.getTextArray(R.styleable.ColorSelector_colors)
//            .map {
//                Color.parseColor(it.toString())
//            }
//        typedArray.recycle()


        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.color_selector, this)
        selectedColor.setBackgroundColor(listOfColors[selectedColorIndex])

        colorSelectorArrowLeft.setOnClickListener{
            selectPreviousColor()
        }

        colorSelectorArrowRight.setOnClickListener {
            selectNextColor()
        }

        colorEnabled.setOnCheckedChangeListener{buttonView, isChecked ->
            broadCastColor()
        }
    }


    var selectedColorValue: Int = android.R.color.transparent
    set(value) {
        var index = listOfColors.indexOf(value)
        if (index == -1){
            colorEnabled.isChecked = false
            index = 0
        }else{
            colorEnabled.isChecked = true
        }
        selectedColorIndex = index
        selectedColor.setBackgroundColor(listOfColors[selectedColorIndex])
    }

    private var colorSelectListeners: ArrayList<(Int) -> Unit> = arrayListOf()

    fun addListener(function: (Int) -> Unit){
        this.colorSelectListeners.add(function)

    }


    private fun selectNextColor() {

        if (selectedColorIndex == listOfColors.lastIndex){
            selectedColorIndex = 0
        }else{
            selectedColorIndex++
        }
      selectedColor.setBackgroundColor(listOfColors[selectedColorIndex])

        broadCastColor()
    }

    private fun selectPreviousColor() {
        if (selectedColorIndex == 0){
            selectedColorIndex = listOfColors.lastIndex
        }else{
            selectedColorIndex--
        }

        selectedColor.setBackgroundColor(listOfColors[selectedColorIndex])

        broadCastColor()
    }



    private fun broadCastColor(){
        val color = if (colorEnabled.isChecked)
            listOfColors[selectedColorIndex]
        else
            Color.TRANSPARENT

        this.colorSelectListeners.forEach{function ->
            function(color)
        }
    }


}