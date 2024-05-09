package com.example.matrixcode

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import java.lang.Runnable
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import kotlinx.coroutines.*

// Click the screen to use the app

/**
 * View is started twice for soe reason
 * future:
 *  - display a consecutive vertical gap between characters
 *      - maybe manually input " " withing the same index 1 to 4 consecutive times
 *  - Take different screen sizes into account
 *      - 'xAxisChars' an 'yAxisChars' values are a cording to the screen size
 */

class MainActivity : AppCompatActivity() {

    private val mainTAG = "Main_TAG"
//    private val handler = Handler(Looper.getMainLooper())
    private lateinit var job: Job
    private var counter = 1
    private val xAxisChars = 39
    private val yAxisChars = 48
    private lateinit var matrixTxt: TextView
    private val allLetters = listOf(
        "Z", "X", "->", "$", "*", "0", "1", "0", "1", "0", "1", "0", "1", "8",
        "&", "@", "]{", "^", "~", "<", ">", "+", " ", " "," "," "," "," "
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.black)
//            window.navigationBarColor = resources.getColor(R.color.black, null)
        }
        matrixTxt = findViewById(R.id.matrix_txt)

        Log.d(mainTAG, "onCreate: called")
        matrixTxt.setOnClickListener {
            populateY(makeXAxis())
            populateMatrixTxt()
        }

        job = CoroutineScope(Dispatchers.Main).launch {
            while (isActive) { // Continues until the coroutine is active
                delay(500) // Wait for 2 seconds
                populateY(makeXAxis())
                populateMatrixTxt()
                counter++
            }
        }

        // Using a Runnable
//        val updateTextRunnable = object : Runnable {
//            override fun run() {
//                // Update the TextView
//                populateY(makeXAxis())
//                populateMatrixTxt()
//                counter++
//
//                if (counter < 1000000) {
//                    // Reschedule the same runnable to run again after 2 seconds
//                    handler.postDelayed(this, 500)
//                }
//            }
//        }
//        // Start it
//        handler.postDelayed(updateTextRunnable, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun populateMatrixTxt() {
        var matrixString = ""
        for (i in 0 until yAxisQueue.size) {
            matrixString = "${yAxisQueue[i]}\n$matrixString"
        }
        matrixTxt.text = matrixString
    }

    private fun makeXAxis(): String {
        var xAxisString = ""
        for (i in 0 until xAxisChars) {
            xAxisString = "$xAxisString ${allLetters[allLetters.indices.random()]}"
        }
        return xAxisString
    }

    private fun populateY(xAxis: String) {
        yAxisQueue.add(xAxis)
        if (yAxisQueue.size > yAxisChars) yAxisQueue.remove()
    }
}