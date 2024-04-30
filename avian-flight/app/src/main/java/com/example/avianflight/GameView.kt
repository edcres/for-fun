package com.example.avianflight

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import kotlin.math.log

// todo: instead of adding and removing items from a list, just have one list with 4 pipe sets
//          - and just change the position for each pipe in the list
// The way is is now it creates a ConcurrentModificationException
//      - and fixing it is resource intensive

class GameView(context: Context) : View(context), ViewTreeObserver.OnGlobalLayoutListener {
    // GameState
    private var gameOn = false
    private var canAddPipe = false
    // Test
    private var testCounter = 0 // debug
    private var pipes = mutableListOf<Pipe>()
    // Bird moves var
    private var birdStartY: Float = 0f
    private var birdY: Float = 0f
    private var birdStartVelocity: Float = 5f
    private var birdVelocity: Float = birdStartVelocity
    // Bird moves const
    private val jumpVelocity: Float = 15f  // Downward velocity on jump
    private val gravity: Float = -0.5f  // Upward acceleration due to gravity
    private val pipeSpeed: Float = 6f
    // Constants
    private val pipeYGap = 500 // minus minPipeY
    private val minPipeY: Int = 100
    private val gapXPipe: Float = 500f
    // Drawing Constants
    private val bottomEdgeHeight: Float = 200f
    private val pipeWidth: Float = 150f
    private val birdRadius: Float = 20f
    private val birdPaint: Paint = Paint().apply { color = Color.CYAN }
    private val pipePaint: Paint = Paint().apply { color = Color.RED }
    private val bottomEdgePaint = Paint().apply { color = Color.parseColor("#910303") }

    init {
        setBackgroundColor(Color.parseColor("#be1010"))
        viewTreeObserver.addOnGlobalLayoutListener(this)
    }

    override fun onGlobalLayout() {
        viewTreeObserver.removeOnGlobalLayoutListener(this)
        birdStartY = (height-bottomEdgeHeight)/2f
        birdY = birdStartY
        initializePipes()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (!gameOn) {
            gameOn = true
            postInvalidateOnAnimation()
            restartGame()
        }
        if (event?.action == MotionEvent.ACTION_DOWN) birdVelocity = -jumpVelocity
        return true
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
                pipe.xGap -= pipeSpeed
                canvas?.drawRect(pipe.xGap, pipe.top, pipe.xGap + pipeWidth, pipe.bottom, pipePaint)
                // If pipe left the screen, add new pipe
                Log.d("TAGTest3", "onDraw: to remove, pipe passed ${pipe.xGap + pipeWidth < 0}, pipes = ${pipes.size}")
                if (pipe.xGap + pipeWidth < 0 && !pipeSetRemoved) {
                    canAddPipe = !canAddPipe
//                    Log.d("TAGTest1B", "onDraw: pipeX = ${pipe.x}; pipeXEnd = ${pipe.x + pipeWidth}")
                    Log.d("TAGTest4", "onDraw: to remove, pipe at ${pipes[0].xGap + pipeWidth}?, pipeSets = ${pipes.size/2}")
                    iterator.remove()
                    Log.d("TAGTest5", "onDraw: removed, left pipe at ${pipes[0].xGap + pipeWidth}, pipeSets = ${pipes.size/2}")
//                    Log.d("TAGTest2A", "onDraw: called: $testCounter newPipeSets = ${newPipes.size/2}")
                    val xGap = 2 * gapXPipe + 2 * pipeWidth
                    val randomGapTop = getRandomGapTop()
                    newPipes.add(Pipe(xGap, 0f, randomGapTop))
                    newPipes.add(Pipe(xGap, randomGapTop, height.toFloat() - bottomEdgeHeight))
                    Log.d("TAGTest6", "onDraw: pipeAdded")
//                    Log.d("TAGTest2B", "onDraw: called: $testCounter newPipeSets = ${newPipes.size/2}")
                    // TODO: I think this if statement should only happen once inside the while loop and it happens more than once
                    pipeSetRemoved = true
                }
            }
            // Add new pipes only after iteration is complete
//            Log.d("TAGTest2C", "onDraw: called: $testCounter newPipeSets = ${newPipes.size/2}")
//            Log.d("TAGTest3", "onDraw: called: $testCounter pipeSets = ${pipes.size/2}")
            pipes.addAll(newPipes)
//            newPipes.clear()
            Log.d("TAGTest4", "onDraw: called: $testCounter, newPipeSets = ${newPipes.size/2}, pipeSets = ${pipes.size/2} \n.")
        } else {
            // Draw pipes stopped
            while (iterator.hasNext()) {
                val pipe = iterator.next()
                canvas?.drawRect(pipe.xGap, pipe.top, pipe.xGap + pipeWidth, pipe.bottom, pipePaint)
            }
        }

        // Draw bottom edge
        canvas?.drawRect(
            0f, height.toFloat() - bottomEdgeHeight,
            width.toFloat(), height.toFloat(), bottomEdgePaint
        )

        // Handle collision, e.g., end game or reset
        if (collisionDetected()) gameOn = false

        // Triggers onDraw
        if (gameOn) postInvalidateOnAnimation()
    }

    private fun initializePipes() {
        // TODO: just change the position values of the pipes, rework this
        for (i in 1..3) {
            val initialXGap = i * gapXPipe + 650
            val randomYGapTop = getRandomGapTop()
            val pipeBottom = height.toFloat() - bottomEdgeHeight
            pipes.add(Pipe(initialXGap, 0f, randomYGapTop))
            pipes.add(Pipe(initialXGap, getRandomGapBottom(randomYGapTop), pipeBottom))
        }
    }

    private fun getRandomGapTop(): Float {
        val maxYGapTop = ((height-bottomEdgeHeight)/1.5f).toInt()
        return (minPipeY..maxYGapTop).random().toFloat()
    }

    private fun getRandomGapBottom(randomGapTop: Float): Float {
        return randomGapTop + pipeYGap - minPipeY
    }

    private fun collisionDetected(): Boolean {
        // Pipe collision
        // TODO: fix pipe collision
        for (pipe in pipes) {
            if (pipe.xGap < 100f + birdRadius && pipe.xGap + pipeWidth > 100f - birdRadius) {
                if (birdY - birdRadius < pipe.bottom || birdY + birdRadius > pipe.top) {
                    return true  // Collision detected
                }
            }
        }
        // Ceiling or Floor Collision
        return birdY < (0 + birdRadius-5) || birdY > (height - bottomEdgeHeight - birdRadius)
    }

    private fun restartGame() {
        birdVelocity = birdStartVelocity
        birdY = birdStartY
    }
}
