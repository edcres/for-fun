package com.example.screencoordinates

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.TextureView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val mainTAG = "main_TAG"
    private lateinit var touchCoordinatesTxt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.black)
        }

        touchCoordinatesTxt = findViewById(R.id.touch_coordinates_txt)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            val coordinatesString = "x = ${event.x}\ny = ${event.y}"
            touchCoordinatesTxt.text = coordinatesString
        } else {
            Log.e(mainTAG, "onTouchEvent: is null")
        }
        return super.onTouchEvent(event)
    }
}