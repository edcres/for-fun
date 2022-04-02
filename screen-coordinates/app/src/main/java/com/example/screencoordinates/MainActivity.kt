package com.example.screencoordinates

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent

class MainActivity : AppCompatActivity() {

    private val mainTAG = "main_TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            val xAxis = event.x
            val yAxis = event.y
        } else {
            Log.e(mainTAG, "onTouchEvent: is null")
        }


        return super.onTouchEvent(event)
    }
}