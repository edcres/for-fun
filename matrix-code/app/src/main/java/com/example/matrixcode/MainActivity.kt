package com.example.matrixcode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.coroutines.*
import java.util.*

// A cool way: https://www.youtube.com/watch?v=25AUSTtob6g

/**
 *
 *
 * future:
 *  - display a consecutive vertical gap between characters
 *      - maybe manually input " " withing the same index 1 to 4 consecutive times
 *  - Take different screen sizes into account
 *      - 'xAxisChars' an 'yAxisChars' values are a cording to the screen size
 */

class MainActivity : AppCompatActivity() {

    private val mainTAG = "Main_TAG"
    private val xAxisChars = 39
    private val yAxisChars = 48
    private lateinit var matrixTxt: TextView
    private val allLetters = listOf(
        "Z", "X", "->", "$", "*", "0", "1", "0", "1", "0", "1", "0", "1", "8",
        "&", "@", "]{", "^", "~", "<", ">", "+", " ", " "," "," "," "," "
    )
    private val yAxisQueue: LinkedList<String> = LinkedList<String>() // add/remove

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        matrixTxt = findViewById(R.id.matrix_txt)

        matrixTxt.setOnClickListener {
            populateY(makeXAxis())
            populateMatrixTxt()
        }

//        while (true) {
//            Log.d(mainTAG, "onCreate: loop")
//            matrixTxt.performClick()
//            runBlocking {
//                delay(500)
//            }
//        }

//        while (true) {
//            CoroutineScope(Dispatchers.IO).launch {
//                populateY(makeXAxis())
//                withContext(Dispatchers.Main) {
//                    populateMatrixTxt()
//                }
//                Thread.sleep(500)
//            }
//        }

//        while (true) {
//            Log.d(mainTAG, "while called")
//            populateY(makeXAxis())
//            Log.d(mainTAG, "yAxisQueue: size = ${yAxisQueue.size} \n${yAxisQueue.last}")
//            Thread.sleep(500)
//            populateMatrixTxt()
//        }
    }

    private fun populateMatrixTxt() {
        var matrixString = ""
        for (i in 0 until yAxisQueue.size) {
            matrixString = "${yAxisQueue[i]}\n$matrixString"
        }
        Log.d(mainTAG, "\n$matrixString")
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
        if (yAxisQueue.size > yAxisChars) {
            yAxisQueue.remove()
        }
    }
}