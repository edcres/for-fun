package com.example.avianflight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gameView = GameView(this)
        supportActionBar?.hide() ?: Log.w("MainAct", "onCreate: Action bar is null")
        setContentView(gameView)
    }
}