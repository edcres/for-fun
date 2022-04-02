package com.example.screencoordinates

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.TouchDelegate
import android.view.View

class CircleView(context: Context): View(context) {

    private val paint: Paint = Paint()

    init {
        paint.isFilterBitmap = true
        paint.isAntiAlias = true
        paint.color = Color.MAGENTA
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas?.drawColor(Color.CYAN)
        canvas?.drawCircle(200f,200f, 50f, paint)
    }

    override fun getTouchDelegate(): TouchDelegate {
        return super.getTouchDelegate()
    }
}