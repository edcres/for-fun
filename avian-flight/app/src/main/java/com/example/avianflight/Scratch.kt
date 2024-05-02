package com.example.avianflight

// maybe remove mutability from the mutable list of pipes

// 6 -> 8 -> 10 -> 12 -> 16 -> 20 -> 24 -> 32 -> 40 -> 48
// every 3:  x-1+2 ; x-2+4 ; x-4+8 ; x-8+16
// 3033; 3123; 3134; 4044
// 011, 011
// -4; -8
// it goes from 7 to 8 very fast

// remove first pipe set when gone
// a shitton of pipes are added when
//      - one is removed, i only want to add one
// add pipes correctly
// gets very laggy after like 6 pipes
//  - (probably because theres a shitton of pipes after some time)
//
// make the gravity feel better
// when game  is over , have a way to restart it
//make the code look better (cleaner)
//
// explore other overriding functions from the canvas library like (onDraw())


class Scratch {

}

// before reworking pipes list
//package com.example.avianflight
//import android.content.Context
//import android.graphics.Canvas
//import android.graphics.Color
//import android.graphics.Paint
//import android.util.Log
//import android.view.MotionEvent
//import android.view.View
//import android.view.ViewTreeObserver
//class GameView(context: Context) : View(context), ViewTreeObserver.OnGlobalLayoutListener {
//    // GameState
//    private var gameOn = false
//    private var canAddPipe = false
//    private var pipes = mutableListOf<Pipe>()
//    // Test
//    private var testCounter = 0 // debug
//    // Bird moves var
//    private var birdStartY: Float = 0f
//    private var birdY: Float = 0f
//    private var birdStartVelocity: Float = 5f
//    private var birdVelocity: Float = birdStartVelocity
//    // Bird moves const
//    private val jumpVelocity: Float = 15f  // Downward velocity on jump
//    private val gravity: Float = -0.5f  // Upward acceleration due to gravity
//    private val pipeSpeed: Float = 10f
//    // Constants
//    private val pipeYGap = 500 // minus minPipeY
//    private val minPipeY: Int = 100
//    private val pipeGapX: Float = 500f
//    // Drawing Constants
//    private val bottomEdgeHeight: Float = 200f
//    private val pipeWidth: Float = 150f
//    private val birdRadius: Float = 20f
//    private val birdX: Float = 100f
//    private val birdPaint: Paint = Paint().apply { color = Color.CYAN }
//    private val pipePaint: Paint = Paint().apply { color = Color.RED }
//    private val bottomEdgePaint = Paint().apply { color = Color.parseColor("#910303") }
//
//    init {
//        setBackgroundColor(Color.parseColor("#be1010"))
//        viewTreeObserver.addOnGlobalLayoutListener(this)
//    }
//
//    override fun onGlobalLayout() {
//        viewTreeObserver.removeOnGlobalLayoutListener(this)
//        birdStartY = (height-bottomEdgeHeight)/2f
//        birdY = birdStartY
//        initializePipes()
//    }
//
//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        if (!gameOn) {
//            gameOn = true
//            postInvalidateOnAnimation()
//            restartGame()
//        }
//        if (event?.action == MotionEvent.ACTION_DOWN) birdVelocity = -jumpVelocity
//        return true
//    }
//
//    override fun onDraw(canvas: Canvas?) {
//        super.onDraw(canvas)
////        Log.d("TAGTest1", "onDrawDraw: called $testCounter")
//
//        testCounter++
//        val newPipes = mutableListOf<Pipe>()
//        Log.d("TAGTest1", "onDraw: called: $testCounter pipeSets = ${pipes.size/2}")
//        val iterator = pipes.iterator()
//        // draw bird
//        canvas?.drawCircle(birdX, birdY, birdRadius, birdPaint)
//        if (gameOn) {
//            var pipeSetRemoved = false
//            // Move Bird
//            birdVelocity -= gravity
//            birdY -= birdVelocity
//
//            // Draw Pipes
//            while (iterator.hasNext()) {
//                val pipe = iterator.next()
//                pipe.xGap -= pipeSpeed
//                canvas?.drawRect(pipe.xGap, pipe.top, pipe.xGap + pipeWidth, pipe.bottom, pipePaint)
//                // If pipe left the screen, add new pipe
////                Log.d("TAGTest3", "onDraw: to remove, pipe passed ${pipe.xGap + pipeWidth < 0}, pipes = ${pipes.size}")
//                if (pipe.xGap + pipeWidth < 0 && !pipeSetRemoved) {
//                    canAddPipe = !canAddPipe
////                    Log.d("TAGTest1B", "onDraw: pipeX = ${pipe.x}; pipeXEnd = ${pipe.x + pipeWidth}")
////                    Log.d("TAGTest4", "onDraw: to remove, pipe at ${pipes[0].xGap + pipeWidth}?, pipeSets = ${pipes.size/2}")
//                    iterator.remove()
////                    Log.d("TAGTest5", "onDraw: removed, left pipe at ${pipes[0].xGap + pipeWidth}, pipeSets = ${pipes.size/2}")
////                    Log.d("TAGTest2A", "onDraw: called: $testCounter newPipeSets = ${newPipes.size/2}")
//                    val xGap = 2 * pipeGapX + 2 * pipeWidth
//                    val randomGapTop = getRandomGapTop()
//                    newPipes.add(Pipe(xGap, 0f, randomGapTop))
//                    newPipes.add(Pipe(xGap, randomGapTop, height.toFloat() - bottomEdgeHeight))
////                    Log.d("TAGTest6", "onDraw: pipeAdded")
////                    Log.d("TAGTest2B", "onDraw: called: $testCounter newPipeSets = ${newPipes.size/2}")
//                    // TODO: I think this if statement should only happen once inside the while loop and it happens more than once
//                    pipeSetRemoved = true
//                }
//            }
//            // Add new pipes only after iteration is complete
////            Log.d("TAGTest2C", "onDraw: called: $testCounter newPipeSets = ${newPipes.size/2}")
////            Log.d("TAGTest3", "onDraw: called: $testCounter pipeSets = ${pipes.size/2}")
//            pipes.addAll(newPipes)
////            newPipes.clear()
////            Log.d("TAGTest4", "onDraw: called: $testCounter, newPipeSets = ${newPipes.size/2}, pipeSets = ${pipes.size/2} \n.")
//        } else {
//            // Draw pipes stopped
//            while (iterator.hasNext()) {
//                val pipe = iterator.next()
//                canvas?.drawRect(pipe.xGap, pipe.top, pipe.xGap + pipeWidth, pipe.bottom, pipePaint)
//            }
//        }
//
//        // Draw bottom edge
//        canvas?.drawRect(
//            0f, height.toFloat() - bottomEdgeHeight,
//            width.toFloat(), height.toFloat(), bottomEdgePaint
//        )
//
//        // Handle collision, e.g., end game or reset
//        if (birdCollisionDetected()) gameOn = false
//
//        // Triggers onDraw
//        if (gameOn) postInvalidateOnAnimation()
//    }
//
//    private fun initializePipes() {
//        // TODO: just change the position values of the pipes, rework this
//        for (i in 1..3) {
//            val initialXGap = i * pipeGapX + 650
//            val randomYGapTop = getRandomGapTop()
//            val pipeBottom = height.toFloat() - bottomEdgeHeight
//            pipes.add(Pipe(initialXGap, 0f, randomYGapTop))
//            pipes.add(Pipe(initialXGap, getRandomGapBottom(randomYGapTop), pipeBottom))
//        }
//    }
//
//    private fun getRandomGapTop(): Float {
//        val maxYGapTop = ((height-bottomEdgeHeight)/1.5f).toInt()
//        return (minPipeY..maxYGapTop).random().toFloat()
//    }
//
//    private fun getRandomGapBottom(randomGapTop: Float): Float = randomGapTop + pipeYGap - minPipeY
//
//    private fun birdCollisionDetected(): Boolean {
//        // Check only first 2 pipes (top and bottom)
//        var pipePos = 0
//        // Only loop through top pipes
//        for (i in 2 until pipes.size)
//            if (i % 2 == 0 && pipes[pipePos].xGap > pipes[i].xGap) pipePos = i
//        for (i in pipePos..(pipePos + 1)) {
//            // X axis
//            if (pipes[i].xGap < birdX + birdRadius
//                &&
//                pipes[i].xGap + pipeWidth > birdX - birdRadius
//            ) {
//                // Top Pipe
//                if (i == pipePos && birdY - birdRadius < pipes[i].bottom) return true
//                // Bottom Pipe
//                else if (i == pipePos + 1 && birdY + birdRadius > pipes[i].top) return true
//            }
//        }
//        // Ceiling or Floor Collision
//        return birdY < (0 + birdRadius-5) || birdY > (height - bottomEdgeHeight - birdRadius)
//    }
//
//    private fun restartGame() {
//        birdVelocity = birdStartVelocity
//        birdY = birdStartY
//    }
//}




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
