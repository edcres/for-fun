package com.example.screencoordinates

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
//import android.util.Log
//import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat


class CircleView(
    context: Context?, private val touchCoordinates: TouchCoordinates
) : View(context) {

    private val circleViewTAG = "CircleView_TAG"
//    var mBitmap: Bitmap = Bitmap.createBitmap(400, 800, Bitmap.Config.ARGB_8888)
    private var circle: Paint = Paint()

    init {
        circle.color = ContextCompat.getColor(context!!, R.color.circle_color)
        circle.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(touchCoordinates.xCor, touchCoordinates.yCor-50f, 25f, circle)
//        invalidate()
    }

//    override fun onTouchEvent(event: MotionEvent): Boolean {
//        performClick()
//        if (event.action == MotionEvent.ACTION_MOVE)
//            Log.d(circleViewTAG, "onTouchEvent: dragged")
//        if (event.action == MotionEvent.ACTION_DOWN)
//            Log.d(circleViewTAG, "onTouchEvent: down")
////        MotionEvent.ACTION_UP
//        return false
//    }

//    override fun performClick(): Boolean {
//        Log.d(circleViewTAG, "performClick: click")
//        return super.performClick()
//    }
}