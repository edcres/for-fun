package com.example.avianflight

// Take out top appbar
// fix pipe collision
//  - gets very laggy after like 6 pipes (probably because theres a shitton of pipies after some time)
//
// jump higher
// make the gravity feel better
// when game  is over , have a way to restart it
//make the code look better (cleaner) (like moving out the data class)
// gap every 2 pipes


class Scratch {
}

// maybe fixed pipe gap problem but has concurrency exception again
//import android.content.Context
//import android.graphics.Canvas
//import android.graphics.Color
//import android.graphics.Paint
//import android.view.MotionEvent
//import android.view.View
//import android.view.ViewTreeObserver
//import java.util.*
//
//class com.example.avianflight.GameView(context: Context) : View(context), ViewTreeObserver.OnGlobalLayoutListener {
//    private var birdY: Float = 100f
//    private var birdVelocity: Float = 0f
//    private val birdPaint: Paint = Paint().apply { color = Color.RED }
//    private val pipePaint: Paint = Paint().apply { color = Color.GREEN }
//    private val gravity: Float = -0.5f
//    private val jumpVelocity: Float = 10f
//    private val birdRadius: Float = 20f
//
//    private var pipes = mutableListOf<Pipe>()
//    private val pipeWidth: Float = 150f
//    private val pipeGap: Float = 300f  // Constant gap size
//    private val pipeSpeed: Float = 4f
//
//    init {
//        birdVelocity = 5f
//        viewTreeObserver.addOnGlobalLayoutListener(this)
//    }
//
//    override fun onGlobalLayout() {
//        viewTreeObserver.removeOnGlobalLayoutListener(this)
//        initializePipes()
//    }
//
//    private fun initializePipes() {
//        for (i in 1..2) {
//            val initialX = i * 500f + width
//            val topPipeBottom = randomGapTop()
//            pipes.add(Pipe(initialX, 0f, topPipeBottom))
//            pipes.add(Pipe(initialX, topPipeBottom + pipeGap, height.toFloat()))
//        }
//    }
//
//    private fun randomGapTop(): Float {
//        // Ensure the top gap leaves enough space for the constant bottom gap
//        val bound = (height - pipeGap).coerceAtLeast(1f).toInt()
//        return Random().nextInt(bound).toFloat()
//    }
//
//    override fun onDraw(canvas: Canvas?) {
//        super.onDraw(canvas)
//
//        birdVelocity -= gravity
//        birdY -= birdVelocity
//        canvas?.drawCircle(100f, birdY, birdRadius, birdPaint)
//
//        val iterator = pipes.iterator()
//        while (iterator.hasNext()) {
//            val pipe = iterator.next()
//            pipe.x -= pipeSpeed
//            canvas?.drawRect(pipe.x, pipe.top, pipe.x + pipeWidth, pipe.bottom, pipePaint)
//
//            if (pipe.x + pipeWidth < 0) {
//                iterator.remove()
//                val newX = width + 100f
//                val newTopPipeBottom = randomGapTop()
//                pipes.add(Pipe(newX, 0f, newTopPipeBottom))
//                pipes.add(Pipe(newX, newTopPipeBottom + pipeGap, height.toFloat()))
//            }
//        }
//
//        if (checkCollision()) {
//            birdY = height / 2f
//            birdVelocity = 5f
//        }
//
//        postInvalidateOnAnimation()
//    }
//
//    private fun checkCollision(): Boolean {
//        for (pipe in pipes) {
//            if (pipe.x < 100f + birdRadius && pipe.x + pipeWidth > 100f - birdRadius) {
//                if (birdY - birdRadius < pipe.bottom && birdY + birdRadius > pipe.top) {
//                    return true  // Collision detected
//                }
//            }
//        }
//        return false  // No collision
//    }
//
//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        if (event?.action == MotionEvent.ACTION_DOWN) {
//            birdVelocity = -jumpVelocity
//        }
//        return true
//    }
//
//    data class Pipe(var x: Float, var top: Float, var bottom: Float)
//}


// before fixing gap problem
//import android.content.Context
//import android.graphics.Canvas
//import android.graphics.Color
//import android.graphics.Paint
//import android.view.MotionEvent
//import android.view.View
//import android.view.ViewTreeObserver
//import java.util.*
//
//class com.example.avianflight.GameView(context: Context) : View(context), ViewTreeObserver.OnGlobalLayoutListener {
//    private var birdY: Float = 100f
//    private var birdVelocity: Float = 0f
//    private val birdPaint: Paint = Paint().apply { color = Color.RED }
//    private val pipePaint: Paint = Paint().apply { color = Color.GREEN }
//    private val gravity: Float = -0.5f  // Upward acceleration due to gravity
//    private val jumpVelocity: Float = 10f  // Downward velocity on jump
//    private val birdRadius: Float = 20f  // Radius of the bird
//
//    private var pipes = mutableListOf<Pipe>()
//    private var newPipes = mutableListOf<Pipe>()
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
//        viewTreeObserver.removeOnGlobalLayoutListener(this)
//        initializePipes()
//    }
//
//    private fun initializePipes() {
//        for (i in 1..2) {
//            val initialX = i * 500f + width
//            pipes.add(Pipe(initialX, 0f, randomGapTop()))
//            pipes.add(Pipe(initialX, randomGapBottom(), height.toFloat()))
//        }
//    }
//
//    private fun randomGapTop(): Float {
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
//        birdVelocity -= gravity
//        birdY -= birdVelocity
//        canvas?.drawCircle(100f, birdY, birdRadius, birdPaint)
//
//        // Perform operations on pipes
//        val iterator = pipes.iterator()
//        while (iterator.hasNext()) {
//            val pipe = iterator.next()
//            pipe.x -= pipeSpeed
//            canvas?.drawRect(pipe.x, pipe.top, pipe.x + pipeWidth, pipe.bottom, pipePaint)
//
//            if (pipe.x + pipeWidth < 0) {
//                iterator.remove()
//                val newX = width + 100f
//                newPipes.add(Pipe(newX, 0f, randomGapTop()))
//                newPipes.add(Pipe(newX, randomGapBottom(), height.toFloat()))
//            }
//        }
//
//        // Add new pipes only after iteration is complete
//        pipes.addAll(newPipes)
//        newPipes.clear()
//
//        if (checkCollision()) {
//            // Handle collision, e.g., end game or reset
//            birdY = height / 2f
//            birdVelocity = 5f
//        }
//
//        postInvalidateOnAnimation()
//    }
//
//    private fun checkCollision(): Boolean {
//        for (pipe in pipes) {
//            if (pipe.x < 100f + birdRadius && pipe.x + pipeWidth > 100f - birdRadius) {
//                if (birdY - birdRadius < pipe.bottom || birdY + birdRadius > pipe.top) {
//                    return true  // Collision detected
//                }
//            }
//        }
//        return false  // No collision
//    }
//
//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        if (event?.action == MotionEvent.ACTION_DOWN) {
//            birdVelocity = -jumpVelocity
//        }
//        return true
//    }
//
//    data class Pipe(var x: Float, var top: Float, var bottom: Float)
//}


// another ConcurrentModificationException
//import android.content.Context
//import android.graphics.Canvas
//import android.graphics.Color
//import android.graphics.Paint
//import android.view.MotionEvent
//import android.view.View
//import android.view.ViewTreeObserver
//import java.util.*
//
//class com.example.avianflight.GameView(context: Context) : View(context), ViewTreeObserver.OnGlobalLayoutListener {
//    private var birdY: Float = 100f
//    private var birdVelocity: Float = 0f
//    private val birdPaint: Paint = Paint().apply { color = Color.RED }
//    private val pipePaint: Paint = Paint().apply { color = Color.GREEN }
//    private val gravity: Float = -0.5f  // Upward acceleration due to gravity
//    private val jumpVelocity: Float = 10f  // Downward velocity on jump
//    private val birdRadius: Float = 20f  // Radius of the bird
//
//    private var pipes = mutableListOf<Pipe>()  // Pipes are now a MutableList
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
//        viewTreeObserver.removeOnGlobalLayoutListener(this)
//        initializePipes()
//    }
//
//    private fun initializePipes() {
//        for (i in 1..2) {
//            val initialX = i * 500f + width
//            pipes.add(Pipe(initialX, 0f, randomGapTop()))
//            pipes.add(Pipe(initialX, randomGapBottom(), height.toFloat()))
//        }
//    }
//
//    private fun randomGapTop(): Float {
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
//        birdVelocity -= gravity
//        birdY -= birdVelocity
//        canvas?.drawCircle(100f, birdY, birdRadius, birdPaint)
//
//        val iterator = pipes.iterator()
//        while (iterator.hasNext()) {
//            val pipe = iterator.next()
//            pipe.x -= pipeSpeed
//            canvas?.drawRect(pipe.x, pipe.top, pipe.x + pipeWidth, pipe.bottom, pipePaint)
//
//            if (pipe.x + pipeWidth < 0) {
//                iterator.remove()
//                val newX = width + 100f
//                pipes.add(Pipe(newX, 0f, randomGapTop()))
//                pipes.add(Pipe(newX, randomGapBottom(), height.toFloat()))
//            }
//        }
//
//        if (checkCollision()) {
//            // Handle collision, e.g., end game or reset
//            birdY = height / 2f
//            birdVelocity = 5f
//        }
//
//        postInvalidateOnAnimation()
//    }
//
//    private fun checkCollision(): Boolean {
//        // Simple bounding box collision detection
//        for (pipe in pipes) {
//            if (pipe.x < 100f + birdRadius && pipe.x + pipeWidth > 100f - birdRadius) {
//                if (birdY - birdRadius < pipe.bottom || birdY + birdRadius > pipe.top) {
//                    return true  // Collision detected
//                }
//            }
//        }
//        return false  // No collision
//    }
//
//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        if (event?.action == MotionEvent.ACTION_DOWN) {
//            birdVelocity = -jumpVelocity
//        }
//        return true
//    }
//
//    data class Pipe(var x: Float, var top: Float, var bottom: Float)
//}



// before colision with pipes
//import android.content.Context
//import android.graphics.Canvas
//import android.graphics.Color
//import android.graphics.Paint
//import android.view.MotionEvent
//import android.view.View
//import android.view.ViewTreeObserver
//import java.util.*
//import kotlin.collections.ArrayList
//
//class com.example.avianflight.GameView(context: Context) : View(context), ViewTreeObserver.OnGlobalLayoutListener {
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
//        viewTreeObserver.removeOnGlobalLayoutListener(this)
//        initializePipes()
//    }
//
//    private fun initializePipes() {
//        for (i in 1..2) {
//            val initialX = i * 500f + width
//            pipes.add(Pipe(initialX, 0f, randomGapTop()))
//            pipes.add(Pipe(initialX, randomGapBottom(), height.toFloat()))
//        }
//    }
//
//    private fun randomGapTop(): Float {
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
//        birdVelocity -= gravity
//        birdY -= birdVelocity
//        canvas?.drawCircle(100f, birdY, 20f, birdPaint)
//
//        val iterator = pipes.iterator()
//        val newPipesToAdd = mutableListOf<Pipe>()
//
//        var topOrBottomTest = 0 // for test TODO: remove
//        while (iterator.hasNext()) {
//            val pipe = iterator.next()
//            pipe.x -= pipeSpeed
//
//
//            if (topOrBottomTest%2 == 0) {
//                canvas?.drawRect(pipe.x, pipe.top, pipe.x + pipeWidth, pipe.bottom, pipePaint)
//            } else {
//                canvas?.drawRect(pipe.x, pipe.top, pipe.x + pipeWidth, pipe.bottom, Paint().apply { color = Color.BLUE })
//            }
//            topOrBottomTest++
//
//
//            if (pipe.x + pipeWidth < 0) {
//                iterator.remove()
//                val newX = width + 100f
//                newPipesToAdd.add(Pipe(newX, 0f, randomGapTop()))
//                newPipesToAdd.add(Pipe(newX, randomGapBottom(), height.toFloat()))
//            }
//        }
//
//        // Add all collected new pipes here
//        pipes.addAll(newPipesToAdd)
//
//        if (checkCollision()) {
//            birdY = height / 2f
//            birdVelocity = 5f
//        }
//
//        postInvalidateOnAnimation()
//    }
//
//    private fun checkCollision(): Boolean {
//        return pipes.any { pipe ->
//            100f in pipe.x..(pipe.x + pipeWidth) && (birdY <= pipe.bottom || birdY >= pipe.top)
//        }
//    }
//
//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        if (event?.action == MotionEvent.ACTION_DOWN) {
//            birdVelocity = -jumpVelocity
//        }
//        return true
//    }
//
//    data class Pipe(var x: Float, var top: Float, var bottom: Float)
//}





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
//class com.example.avianflight.GameView(context: Context) : View(context), ViewTreeObserver.OnGlobalLayoutListener {
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
//class com.example.avianflight.GameView(context: Context) : View(context) {
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
