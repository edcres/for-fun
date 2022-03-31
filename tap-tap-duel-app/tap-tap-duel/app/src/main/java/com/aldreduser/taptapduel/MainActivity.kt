package com.aldreduser.taptapduel

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat

// have a timer count down before the game starts
// have a gistory of the battles to names of people battling  (hide the score until the counter is gone)
// Give a dark outline to the letters in the UI

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.black)
        }
    }

    fun getTappingButtonClicked(view: View) {
        val intent = Intent(this, TapBattleActivity::class.java)
        startActivity(intent)
    }
}
