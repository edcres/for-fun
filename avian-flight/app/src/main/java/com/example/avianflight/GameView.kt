import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import java.util.*

class GameView(context: Context) : View(context), ViewTreeObserver.OnGlobalLayoutListener {
    private var birdY: Float = 100f
    private var birdVelocity: Float = 0f
    private val birdPaint: Paint = Paint().apply { color = Color.RED }
    private val pipePaint: Paint = Paint().apply { color = Color.GREEN }
    private val gravity: Float = -0.5f  // Upward acceleration due to gravity
    private val jumpVelocity: Float = 10f  // Downward velocity on jump
    private val birdRadius: Float = 20f  // Radius of the bird

    private var pipes = mutableListOf<Pipe>()
    private var newPipes = mutableListOf<Pipe>()
    private val pipeWidth: Float = 150f
    private val pipeGap: Float = 300f
    private val pipeSpeed: Float = 4f

    init {
        birdVelocity = 5f  // Initial upward movement
        viewTreeObserver.addOnGlobalLayoutListener(this)
    }

    override fun onGlobalLayout() {
        viewTreeObserver.removeOnGlobalLayoutListener(this)
        initializePipes()
    }

    private fun initializePipes() {
        for (i in 1..2) {
            val initialX = i * 500f + width
            pipes.add(Pipe(initialX, 0f, randomGapTop()))
            pipes.add(Pipe(initialX, randomGapBottom(), height.toFloat()))
        }
    }

    private fun randomGapTop(): Float {
        val bound = (height - pipeGap).coerceAtLeast(1f).toInt()
        return Random().nextInt(bound).toFloat()
    }

    private fun randomGapBottom(): Float {
        val top = randomGapTop()
        return top + pipeGap
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        birdVelocity -= gravity
        birdY -= birdVelocity
        canvas?.drawCircle(100f, birdY, birdRadius, birdPaint)

        // Perform operations on pipes
        val iterator = pipes.iterator()
        while (iterator.hasNext()) {
            val pipe = iterator.next()
            pipe.x -= pipeSpeed
            canvas?.drawRect(pipe.x, pipe.top, pipe.x + pipeWidth, pipe.bottom, pipePaint)

            if (pipe.x + pipeWidth < 0) {
                iterator.remove()
                val newX = width + 100f
                newPipes.add(Pipe(newX, 0f, randomGapTop()))
                newPipes.add(Pipe(newX, randomGapBottom(), height.toFloat()))
            }
        }

        // Add new pipes only after iteration is complete
        pipes.addAll(newPipes)
        newPipes.clear()

        if (checkCollision()) {
            // Handle collision, e.g., end game or reset
            birdY = height / 2f
            birdVelocity = 5f
        }

        postInvalidateOnAnimation()
    }

    private fun checkCollision(): Boolean {
        for (pipe in pipes) {
            if (pipe.x < 100f + birdRadius && pipe.x + pipeWidth > 100f - birdRadius) {
                if (birdY - birdRadius < pipe.bottom || birdY + birdRadius > pipe.top) {
                    return true  // Collision detected
                }
            }
        }
        return false  // No collision
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            birdVelocity = -jumpVelocity
        }
        return true
    }

    data class Pipe(var x: Float, var top: Float, var bottom: Float)
}
