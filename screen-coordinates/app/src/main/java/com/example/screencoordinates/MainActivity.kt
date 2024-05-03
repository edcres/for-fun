package com.example.screencoordinates

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.TextureView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val mainTAG = "main_TAG"
    private lateinit var touchCoordinatesTxt: TextView
    private lateinit var circleView: CircleView
    private lateinit var circleContainer: LinearLayout
    private val touchCoordinates = TouchCoordinates()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.main)
        }
        circleContainer = findViewById(R.id.circle_container)
        circleView = CircleView(this, touchCoordinates)
        circleContainer.addView(circleView)
        touchCoordinatesTxt = findViewById(R.id.touch_coordinates_txt)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            touchCoordinates.xCor = event.x
            touchCoordinates.yCor = event.y
            val coordinatesString = "x = ${event.x}\ny = ${event.y}"
            touchCoordinatesTxt.text = coordinatesString
            circleView.invalidate()
        } else {
            Log.e(mainTAG, "onTouchEvent: is null")
        }
        return super.onTouchEvent(event)
    }
}