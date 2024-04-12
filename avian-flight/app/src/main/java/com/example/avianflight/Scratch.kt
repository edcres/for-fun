package com.example.avianflight

class Scratch {
}











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
