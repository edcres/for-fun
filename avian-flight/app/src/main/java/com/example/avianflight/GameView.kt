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
    private var gameOn = false
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
        for (i in 1..3) {
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
//        Log.d("TAGTest1", "onDrawDraw: called $testCounter")
        testCounter++
        val newPipes = mutableListOf<Pipe>()
        Log.d("TAGTest1", "onDraw: called: $testCounter pipeSets = ${pipes.size/2}")
        val iterator = pipes.iterator()
        // draw bird
        canvas?.drawCircle(100f, birdY, birdRadius, birdPaint)
        if (gameOn) {
            var pipeSetRemoved = false
            // Move Bird
            birdVelocity -= gravity
            birdY -= birdVelocity

            // Draw Pipes
            while (iterator.hasNext()) {
                val pipe = iterator.next()
                pipe.x -= pipeSpeed
                canvas?.drawRect(pipe.x, pipe.top, pipe.x + pipeWidth, pipe.bottom, pipePaint)
                // If pipe left the screen, add new pipe
                if (pipe.x + pipeWidth < 0 && !pipeSetRemoved) {
                    Log.d("TAGTest1B", "onDraw: pipeX = ${pipe.x}; pipeXEnd = ${pipe.x + pipeWidth}")
                    iterator.remove()
                    val xGap = 2 * gapXPipe + 2 * pipeWidth
                    Log.d("TAGTest2A", "onDraw: called: $testCounter newPipeSets = ${newPipes.size/2}")
                    newPipes.add(Pipe(xGap, 0f, randomGapTop()))
                    newPipes.add(Pipe(xGap, randomGapBottom(), height.toFloat()))
                    Log.d("TAGTest2B", "onDraw: called: $testCounter newPipeSets = ${newPipes.size/2}")
                    // TODO: I think this if statement should only happen once inside the while loop and it happens more than once
                    pipeSetRemoved = true
                }
            }
            // Add new pipes only after iteration is complete
            Log.d("TAGTest2C", "onDraw: called: $testCounter newPipeSets = ${newPipes.size/2}")
            Log.d("TAGTest3", "onDraw: called: $testCounter pipeSets = ${pipes.size/2}")
            pipes.addAll(newPipes)
//            newPipes.clear()
            Log.d("TAGTest4", "onDraw: called: $testCounter pipeSets = ${pipes.size/2} \n.")
        } else {
            // Draw pipes stopped
            while (iterator.hasNext()) {
                val pipe = iterator.next()
                canvas?.drawRect(pipe.x, pipe.top, pipe.x + pipeWidth, pipe.bottom, pipePaint)
            }
        }

//        if (collisionDetected()) {
//            // Handle collision, e.g., end game or reset
//            birdY = height / 2f
//            birdVelocity = 5f
//            gameOn = false
//        }

        // Triggers onDraw
        postInvalidateOnAnimation()
    }

    private fun collisionDetected(): Boolean {
        // Pipe collision
        // TODO: fix pipe collision
        for (pipe in pipes) {
            if (pipe.x < 100f + birdRadius && pipe.x + pipeWidth > 100f - birdRadius) {
                if (birdY - birdRadius < pipe.bottom || birdY + birdRadius > pipe.top) {
                    return true  // Collision detected
                }
            }
        }
        // Ceiling or Floor Collision
        if (birdY < (0 + birdRadius) || birdY > (height - birdRadius)) return true
        return false
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (!gameOn) gameOn = true
        if (event?.action == MotionEvent.ACTION_DOWN) birdVelocity = -jumpVelocity
        return true
    }

    data class Pipe(var x: Float, var top: Float, var bottom: Float)
}
