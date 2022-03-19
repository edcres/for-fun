package com.aldreduser.taptapduel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_tap_battle.*

// allow user to choose a different number of max taps
// every 10 points the phone vibrates

class TapBattleActivity : AppCompatActivity() {

    var p1Score:Int = 0
    var p2Score:Int = 0
    var maxTaps = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tap_battle)
    }

    fun p1LayoutBoxClicked(view: View) {
        if (p1Score < maxTaps && p2Score < maxTaps){
            p1Score++
            p1ScoreText.text = p1Score.toString()
        } else if(p1Score == maxTaps || p2Score == maxTaps){
            endGame()
        } else {}
    }
    fun p2LayoutBoxClicked(view: View) {
        if (p1Score < maxTaps && p2Score < maxTaps) {
            p2Score++
            p2ScoreText.text = p2Score.toString()
        } else if(p1Score == maxTaps || p2Score == maxTaps){
            endGame()
        } else {}
    }

    fun endGame() {
        if(p1Score > p2Score){
            //p1 wins
            p1ScoreText.text = "YOU WIN!!!"
            p2ScoreText.text = "YOU LOSE ):"
        } else if(p1Score < p2Score){
            //p2 wins
            p1ScoreText.text = "YOU LOSE ):"
            p2ScoreText.text = "YOU WIN!!!"
        } else {
            //tied
            p1ScoreText.text = "It's a tie -__-"
            p2ScoreText.text = "No one won v_v"
        }
    }
}
