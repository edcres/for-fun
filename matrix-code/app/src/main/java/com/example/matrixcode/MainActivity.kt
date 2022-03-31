package com.example.matrixcode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.util.*

// A cool way: https://www.youtube.com/watch?v=25AUSTtob6g

// 39 x 48

class MainActivity : AppCompatActivity() {

    private lateinit var matrixTxt: TextView
    private val allLetters = listOf(
        "Z", "X", "->", "$", "*", "0", "1", "0", "1", "0", "1", "0", "1", "8", "&", "@", "]{", "^", "~", "<", ">", "+",
        " ", " "," "," "," "," "
    )
    private val yAxisQueue: LinkedList<List<String>> = LinkedList<List<String>>()
    // add/remove

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        matrixTxt = findViewById(R.id.matrix_txt)




        var matrixString = ""
        for (i in 0 until yAxisQueue.size) {
            matrixString = "${yAxisQueue[i]}$matrixString"
        }
        matrixTxt.text = matrixString
    }

    private fun populateX() {

    }

    private fun populateY(xAxisList: List<String>) {
        yAxisQueue.add(xAxisList)
    }
}