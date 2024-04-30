package com.example.avianflight

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver

// todo: instead of adding and removing items from a list, just have one list with 4 pipe sets
//          - and just change the position for each pipe in the list
// The way is is now it creates a ConcurrentModificationException
//      - and fixing it is resource intensive

class GameView(context: Context) : View(context), ViewTreeObserver.OnGlobalLayoutListener {
    private var gameOn = false
    private var canAddPipe = false

    private var testCounter = 0 // debug
    private var birdStartY: Float = 0f

    private var birdY: Float = 0f
    private var birdStartVelocity: Float = 5f
    private var birdVelocity: Float = birdStartVelocity

    private var pipes = mutableListOf<Pipe>()

    private val jumpVelocity: Float = 15f  // Downward velocity on jump
    private val gravity: Float = -0.5f  // Upward acceleration due to gravity
    private val pipeSpeed: Float = 6f
    private val pipeYGap = 250 // minus minPipeY
    private val minPipeY: Float = 100f
    private val gapXPipe: Float = 500f
    private val pipeWidth: Float = 150f
    private val birdRadius: Float = 20f
    private val birdPaint: Paint = Paint().apply { color = Color.RED }
    private val pipePaint: Paint = Paint().apply { color = Color.GREEN }

    init {
        viewTreeObserver.addOnGlobalLayoutListener(this)
    }

    override fun onGlobalLayout() {
        viewTreeObserver.removeOnGlobalLayoutListener(this)
        birdStartY = height/2f
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
                pipe.x -= pipeSpeed
                canvas?.drawRect(pipe.x, pipe.top, pipe.x + pipeWidth, pipe.bottom, pipePaint)
                // If pipe left the screen, add new pipe
                Log.d("TAGTest3", "onDraw: to remove, pipe passed ${pipe.x + pipeWidth < 0}, pipes = ${pipes.size}")
                if (pipe.x + pipeWidth < 0 && !pipeSetRemoved) {
                    canAddPipe = !canAddPipe
//                    Log.d("TAGTest1B", "onDraw: pipeX = ${pipe.x}; pipeXEnd = ${pipe.x + pipeWidth}")
                    Log.d("TAGTest4", "onDraw: to remove, pipe at ${pipes[0].x + pipeWidth}?, pipeSets = ${pipes.size/2}")
                    iterator.remove()
                    Log.d("TAGTest5", "onDraw: removed, left pipe at ${pipes[0].x + pipeWidth}, pipeSets = ${pipes.size/2}")
//                    Log.d("TAGTest2A", "onDraw: called: $testCounter newPipeSets = ${newPipes.size/2}")
                    val xGap = 2 * gapXPipe + 2 * pipeWidth
                    val randomGapTop = getRandomGapTop()
                    newPipes.add(Pipe(xGap, 0f, randomGapTop))
                    newPipes.add(Pipe(xGap, randomGapTop, height.toFloat()))
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
                canvas?.drawRect(pipe.x, pipe.top, pipe.x + pipeWidth, pipe.bottom, pipePaint)
            }
        }

        // Handle collision, e.g., end game or reset
        if (collisionDetected()) gameOn = false

        // Triggers onDraw
        if (gameOn) postInvalidateOnAnimation()
    }

    private fun initializePipes() {
        // TODO: just change the position values of the pipes, rework this
        for (i in 1..3) {
            val initialXGap = i * gapXPipe + 650
            val randomGapTop = getRandomGapTop()
            pipes.add(Pipe(initialXGap, 0f, randomGapTop))
            pipes.add(Pipe(initialXGap, getRandomGapBottom(randomGapTop) + minPipeY, height.toFloat()))
        }
    }

    private fun getRandomGapTop(): Float {
//        val bound = (height - pipeYGap).coerceAtLeast(1f).toInt()
//        return Random().nextInt(bound).toFloat()
        val minYGapTop = (height/1.5).toInt()

        Log.d("TAG0", "randomGapTop: screenY = ${height}; minYGapTop = $minYGapTop")
//        val minPipeYTop = height/1.5.toInt()
        val yGapTop = (0..minYGapTop).random().toFloat()
        Log.d("TAG0", "randomGapTop: yGapTop = ${yGapTop}")
        return yGapTop
    }

    private fun getRandomGapBottom(randomGapTop: Float): Float {
        return randomGapTop + pipeYGap
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

    private fun restartGame() {
        birdVelocity = birdStartVelocity
        birdY = birdStartY
        initializePipes()
    }
}
