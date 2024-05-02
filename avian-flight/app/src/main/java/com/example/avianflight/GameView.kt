package com.example.avianflight

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver

class GameView(context: Context) : View(context), ViewTreeObserver.OnGlobalLayoutListener {
    // GameState
    private var gameOn = false
    private var score = 0
    private var pipes = mutableListOf<Pipe>()
    // Bird moves var
    private var birdStartY: Float = 0f
    private var birdY: Float = 0f
    private var birdStartVelocity: Float = 5f
    private var birdVelocity: Float = birdStartVelocity
    // Bird moves const
    private val jumpVelocity: Float = 20f  // Downward velocity on jump
    private val gravity: Float = -1f  // Upward acceleration due to gravity
    private val pipeSpeed: Float = 10f
    // Constants
    private val pipeYGap = 650 // minus minPipeY
    private val minPipeY: Int = 300
    private val pipeGapX: Float = 600f
    // Drawing Constants
    private val bottomEdgeHeight: Float = 200f
    private val pipeWidth: Float = 150f
    private val birdRadius: Float = 20f
    private val birdX: Float = 100f
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
        // Jump
        if (event?.action == MotionEvent.ACTION_DOWN) birdVelocity = -jumpVelocity
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        // Draw bird
        canvas?.drawCircle(birdX, birdY, birdRadius, birdPaint)
        // Move Bird
        birdVelocity -= gravity
        birdY -= birdVelocity
        // Draw Pipes
        // May be better using iterator when drawing pipes
        var previousTopPipe: Pipe? = null
        for (pipe in pipes) {
            // Move pipe
            pipe.xGap -= pipeSpeed
            canvas?.drawRect(
                pipe.xGap, pipe.top, pipe.xGap + pipeWidth, pipe.bottom, pipePaint
            )
            // If pipe left the screen, move it to the end
            if (pipe.xGap + pipeWidth < 0) {
                val xGap = 2 * pipeGapX + 3 * pipeWidth
                val randomGapTop = getRandomGapTop()
                if (previousTopPipe == null) {
                    previousTopPipe = pipe
                } else {
                    pipe.xGap = xGap
                    // Top Pipe
                    previousTopPipe.xGap = xGap
                    previousTopPipe.bottom = randomGapTop
                    previousTopPipe = null
                    // Bottom Pipe
                    pipe.xGap = xGap
                    pipe.top = getRandomGapBottom(randomGapTop)
                }
            }
        }
        // Draw bottom edge
        canvas?.drawRect(
            0f, height.toFloat() - bottomEdgeHeight,
            width.toFloat(), height.toFloat(), bottomEdgePaint
        )
        // Score points
        if (birdPassedPipe()) score++
        Log.d("TAG0", "onDraw: score = $score")
        // Handle collision, e.g., end game or reset
        if (birdCollisionDetected()) gameOn = false
        // Triggers onDraw
        if (gameOn) postInvalidateOnAnimation()
    }

    private fun initializePipes() {
        for (i in 1..3) {
            val initialXGap = i * pipeGapX + 650
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

    private fun getRandomGapBottom(randomGapTop: Float): Float = randomGapTop + pipeYGap - minPipeY

    private fun birdCollisionDetected(): Boolean {
        // Check only first 2 pipes (top and bottom)
        var pipePos = 0
        // Only loop through top pipes
        for (i in 2 until pipes.size)
            if (i % 2 == 0 && pipes[pipePos].xGap > pipes[i].xGap) pipePos = i
        for (i in pipePos..(pipePos + 1)) {
            // X axis
            if (pipes[i].xGap < birdX + birdRadius
                &&
                pipes[i].xGap + pipeWidth > birdX - birdRadius
            ) {
                // Top Pipe
                if (i == pipePos && birdY - birdRadius + 10 < pipes[i].bottom) return true
                // Bottom Pipe
                else if (i == pipePos + 1 && birdY + birdRadius - 5 > pipes[i].top) return true
            }
        }
        // Ceiling or Floor Collision
        return birdY < (0 + birdRadius-5) || birdY > (height - bottomEdgeHeight - birdRadius)
    }

    private fun birdPassedPipe(): Boolean {
        var pipePos = 0
        // Get current pipe (only check top pipes)
        for (i in 2 until pipes.size)
            if (i % 2 == 0 && pipes[pipePos].xGap > pipes[i].xGap) pipePos = i
        return pipes[pipePos].xGap + pipeWidth == birdX - birdRadius
    }

    private fun restartGame() {
        birdVelocity = birdStartVelocity
        birdY = birdStartY
        pipes.clear()
        initializePipes()
        score = 0
    }
}
