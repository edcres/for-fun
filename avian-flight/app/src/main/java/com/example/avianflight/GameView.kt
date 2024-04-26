package com.example.avianflight

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import java.util.*

class GameView(context: Context) : View(context), ViewTreeObserver.OnGlobalLayoutListener {
    private var gameStarted = false
    private var testCounter = 0 // debug
    private var birdY: Float = 700f
    private var birdVelocity: Float = 5f
    private val birdPaint: Paint = Paint().apply { color = Color.RED }
    private val pipePaint: Paint = Paint().apply { color = Color.GREEN }
    private val gravity: Float = -0.5f  // Upward acceleration due to gravity
    private val jumpVelocity: Float = 10f  // Downward velocity on jump
    private val birdRadius: Float = 20f  // Radius of the bird

    private var pipes = mutableListOf<Pipe>()
    private val pipeWidth: Float = 150f
    private val pipeYGap: Float = 100f
    private val pipeSpeed: Float = 4f
    private val gapXPipe: Float = 500f

    init {
        viewTreeObserver.addOnGlobalLayoutListener(this)
    }

    override fun onGlobalLayout() {
        viewTreeObserver.removeOnGlobalLayoutListener(this)
        initializePipes()
    }

    private fun initializePipes() {
        for (i in 1..2) {
            val initialXGap = i * gapXPipe + 650
            pipes.add(Pipe(initialXGap, 0f, randomGapTop()))
            pipes.add(Pipe(initialXGap, randomGapBottom(), height.toFloat()))
        }
    }

    private fun randomGapTop(): Float {
        val bound = (height - pipeYGap).coerceAtLeast(1f).toInt()
        return Random().nextInt(bound).toFloat()
    }

    private fun randomGapBottom(): Float {
        val top = randomGapTop()
        return top + pipeYGap
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.d("TAGTest", "onDraw: called $testCounter")
        testCounter++

        // Draw bird
        if (gameStarted) {
            birdVelocity -= gravity
            birdY -= birdVelocity
            canvas?.drawCircle(100f, birdY, birdRadius, birdPaint)
        }

        // TODO:
        //  - make it so when the first, and one every 3 pipes
        //     after is off screen, a new one is created
        // Add Pipe
//        val newPipes = mutableListOf<Pipe>()
//        val iterator = pipes.iterator()
//        while (iterator.hasNext()) {
//            val pipe = iterator.next()
//            pipe.x -= pipeSpeed
//            canvas?.drawRect(pipe.x, pipe.top, pipe.x + pipeWidth, pipe.bottom, pipePaint)
//
//            if (pipe.x + pipeWidth < 0) {
//                iterator.remove()
//                val xGap = gapXPipe + 2 * pipeWidth + (pipeWidth/4)
//                newPipes.add(Pipe(xGap, 0f, randomGapTop()))
//                newPipes.add(Pipe(xGap, randomGapBottom(), height.toFloat()))
//            }
//        }
//
//        // Add new pipes only after iteration is complete
//        pipes.addAll(newPipes)
//        Log.d("TAGTest2", "onDraw: ${pipes.size}")
//        newPipes.clear()



        if (collisionDetected()) {
            // Handle collision, e.g., end game or reset
            birdY = height / 2f
            birdVelocity = 5f
        }

        postInvalidateOnAnimation()
    }

    private fun collisionDetected(): Boolean {
        // Pipe collision
        for (pipe in pipes) {
            if (pipe.x < 100f + birdRadius && pipe.x + pipeWidth > 100f - birdRadius) {
                if (birdY - birdRadius < pipe.bottom || birdY + birdRadius > pipe.top) {
                    return true  // Collision detected
                }
            }
        }
        // Floor ceiling collision
        return false  // No collision
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (!gameStarted) gameStarted = true
        if (event?.action == MotionEvent.ACTION_DOWN) {
            birdVelocity = -jumpVelocity
        }
        return true
    }

    data class Pipe(var x: Float, var top: Float, var bottom: Float)
}
