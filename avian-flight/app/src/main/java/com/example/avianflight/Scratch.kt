package com.example.avianflight

class Scratch {
}

// with obstacles but there's a bug probably adding an obstacle out of range
//import android.content.Context
//import android.graphics.Canvas
//import android.graphics.Color
//import android.graphics.Paint
//import android.view.MotionEvent
//import android.view.View
//import java.util.*
//import kotlin.collections.ArrayList
//import android.view.ViewTreeObserver
//class GameView(context: Context) : View(context), ViewTreeObserver.OnGlobalLayoutListener {
//    private var birdY: Float = 100f
//    private var birdVelocity: Float = 0f
//    private val birdPaint: Paint = Paint().apply { color = Color.RED }
//    private val pipePaint: Paint = Paint().apply { color = Color.GREEN }
//    private val gravity: Float = -0.5f  // Upward acceleration due to gravity
//    private val jumpVelocity: Float = 10f  // Downward velocity on jump
//
//    private var pipes = ArrayList<Pipe>()
//    private val pipeWidth: Float = 150f
//    private val pipeGap: Float = 300f
//    private val pipeSpeed: Float = 4f
//
//    init {
//        birdVelocity = 5f  // Initial upward movement
//        viewTreeObserver.addOnGlobalLayoutListener(this)
//    }
//
//    override fun onGlobalLayout() {
//        // Ensure we only initialize once and then remove the listener
//        viewTreeObserver.removeOnGlobalLayoutListener(this)
//        initializePipes()
//    }
//
//    private fun initializePipes() {
//        // Initialize pipes at intervals
//        for (i in 1..2) {
//            val initialX = i * 500f + width  // Start off-screen to the right
//            pipes.add(Pipe(initialX, 0f, randomGapTop()))  // Top pipe
//            pipes.add(Pipe(initialX, randomGapBottom(), height.toFloat()))  // Bottom pipe
//        }
//    }
//
//    private fun randomGapTop(): Float {
//        // Ensures that the calculation is always positive
//        val bound = (height - pipeGap).coerceAtLeast(1f).toInt()
//        return Random().nextInt(bound).toFloat()
//    }
//
//    private fun randomGapBottom(): Float {
//        val top = randomGapTop()
//        return top + pipeGap
//    }
//
//    override fun onDraw(canvas: Canvas?) {
//        super.onDraw(canvas)
//
//        // Update bird's position due to gravity
//        birdVelocity -= gravity
//        birdY -= birdVelocity
//
//        // Draw bird
//        canvas?.drawCircle(100f, birdY, 20f, birdPaint)
//
//        // Move and draw pipes
//        val iterator = pipes.iterator()
//        while (iterator.hasNext()) {
//            val pipe = iterator.next()
//            pipe.x -= pipeSpeed
//
//            // Draw pipe
//            canvas?.drawRect(pipe.x, pipe.top, pipe.x + pipeWidth, pipe.bottom, pipePaint)
//
//            // Remove pipe if it's off-screen
//            if (pipe.x + pipeWidth < 0) {
//                iterator.remove()
//                // Add new pipes to replace the old ones
//                val newX = width + 100f  // New pipe starts off-screen
//                pipes.add(Pipe(newX, 0f, randomGapTop()))  // Top pipe
//                pipes.add(Pipe(newX, randomGapBottom(), height.toFloat()))  // Bottom pipe
//            }
//        }
//
//        // Check for collisions
//        if (checkCollision()) {
//            // Handle collision (game over or reset bird position)
//            birdY = height / 2f
//            birdVelocity = 5f
//        }
//
//        // Ensure the animation continues
//        postInvalidateOnAnimation()
//    }
//
//    private fun checkCollision(): Boolean {
//        // Collision logic between bird and pipes
//        return pipes.any { pipe ->
//            100f in pipe.x..(pipe.x + pipeWidth) && (birdY <= pipe.bottom || birdY >= pipe.top)
//        }
//    }
//
//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        if (event?.action == MotionEvent.ACTION_DOWN) {
//            // On touch, bird jumps down
//            birdVelocity = -jumpVelocity
//        }
//        return true
//    }
//
//    data class Pipe(var x: Float, var top: Float, var bottom: Float)
//}




// TODO: handle this '?'
// just the bird falling up
//
//import android.content.Context
//import android.graphics.Canvas
//import android.graphics.Color
//import android.graphics.Paint
//import android.view.MotionEvent
//import android.view.View
//
//class GameView(context: Context) : View(context) {
//    private var birdY: Float = 100f
//    private var birdVelocity: Float = 0f
//    private val birdPaint: Paint = Paint().apply { color = Color.RED }
//    private val gravity: Float = -0.5f  // Upward acceleration due to gravity
//    private val jumpVelocity: Float = 10f  // Downward velocity on jump
//
//    init {
//        // Initial upward movement
//        birdVelocity = 5f
//    }
//
//    override fun onDraw(canvas: Canvas?) {
//        super.onDraw(canvas)
//
//        // Apply gravity effect
//        birdVelocity -= gravity
//
//        // Update bird's position
//        birdY -= birdVelocity  // Subtracting because going up decreases the Y value
//
//        // Draw the bird
//        canvas?.drawCircle(100f, birdY, 20f, birdPaint)
//
//        // Check for boundary conditions (avoid bird going off the screen)
//        if (birdY < 0 || birdY > height) {
//            birdY = height / 2f  // Reset to middle of the screen
//            birdVelocity = 5f    // Reset velocity
//        }
//
//        // Repeat the drawing to create an animation
//        postInvalidateOnAnimation()
//    }
//
//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        if (event?.action == MotionEvent.ACTION_DOWN) {
//            // On touch, the bird jumps down
//            birdVelocity = -jumpVelocity
//        }
//        return true
//    }
//}
