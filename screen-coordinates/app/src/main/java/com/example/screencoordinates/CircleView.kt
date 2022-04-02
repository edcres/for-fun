package com.example.screencoordinates

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat

class CircleView(
    context: Context?, private val touchCoordinates: TouchCoordinates
) : View(context) {

    private val circleViewTAG = "CircleView_TAG"
    var mBitmap: Bitmap = Bitmap.createBitmap(400, 800, Bitmap.Config.ARGB_8888)
    private var paint: Paint = Paint()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(touchCoordinates.xCor, touchCoordinates.yCor, 25f, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        performClick()
        if (event.action == MotionEvent.ACTION_DOWN) {
            touchCoordinates.xCor = event.x
            touchCoordinates.yCor = event.y
            invalidate()
        }
        return false
    }

    override fun performClick(): Boolean {
        Log.d(circleViewTAG, "performClick: called")
        return super.performClick()
    }

    init {
        paint.color = ContextCompat.getColor(context!!, R.color.circle_color)
        paint.style = Paint.Style.FILL
    }
}