package com.example.screencoordinates

import android.R.attr
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View

class CircleView(context: Context?, private val touchCoordinates: TouchCoordinates) : View(context) {
    var mBitmap: Bitmap
    var paint: Paint
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(touchCoordinates.xCor, touchCoordinates.yCor, 50f, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            touchCoordinates.xCor = event.x
            touchCoordinates.yCor = event.y
            invalidate()
        }
        return false
    }

    init {
        mBitmap = Bitmap.createBitmap(400, 800, Bitmap.Config.ARGB_8888)
        paint = Paint()
        paint.color = Color.RED
        paint.style = Paint.Style.FILL
    }
}