package com.example.matrixcode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.util.*

// A cool way: https://www.youtube.com/watch?v=25AUSTtob6g

/**
 *
 *
 * future:
 *  - display a consecutive vertical gap between characters
 *      - maybe manually input " " withing the same index 1 to 4 consecutive times
 */

class MainActivity : AppCompatActivity() {

    private val xAxisChars = 39
    private val yAxisChars = 48
    private lateinit var matrixTxt: TextView
    private val allLetters = listOf(
        "Z", "X", "->", "$", "*", "0", "1", "0", "1", "0", "1", "0", "1", "8", "&", "@", "]{", "^", "~", "<", ">", "+",
        " ", " "," "," "," "," "
    )
    private val yAxisQueue: LinkedList<String> = LinkedList<String>()
    // add/remove

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        matrixTxt = findViewById(R.id.matrix_txt)

        // do an infinite while loop, populateY(), populateMatrixTxt, short delay, repeat
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
            xAxisString = "$xAxisString ${/* todo: insert random letter from the 'allLetters' list */}"
        }
        return xAxisString
    }

    private fun populateY() {
        yAxisQueue.add(makeXAxis())
        if (yAxisQueue.size > yAxisChars) {
            yAxisQueue.remove()
        }
    }
}