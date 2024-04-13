import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import java.util.*
import kotlin.collections.ArrayList

class GameView(context: Context) : View(context), ViewTreeObserver.OnGlobalLayoutListener {
    private var birdY: Float = 100f
    private var birdVelocity: Float = 0f
    private val birdPaint: Paint = Paint().apply { color = Color.RED }
    private val pipePaint: Paint = Paint().apply { color = Color.GREEN }
    private val gravity: Float = -0.5f  // Upward acceleration due to gravity
    private val jumpVelocity: Float = 10f  // Downward velocity on jump

    private var pipes = ArrayList<Pipe>()
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
        canvas?.drawCircle(100f, birdY, 20f, birdPaint)

        val iterator = pipes.iterator()
        val newPipesToAdd = mutableListOf<Pipe>()

        while (iterator.hasNext()) {
            val pipe = iterator.next()
            pipe.x -= pipeSpeed
            canvas?.drawRect(pipe.x, pipe.top, pipe.x + pipeWidth, pipe.bottom, pipePaint)

            if (pipe.x + pipeWidth < 0) {
                iterator.remove()
                val newX = width + 100f
                newPipesToAdd.add(Pipe(newX, 0f, randomGapTop()))
                newPipesToAdd.add(Pipe(newX, randomGapBottom(), height.toFloat()))
            }
        }

        // Add all collected new pipes here
        pipes.addAll(newPipesToAdd)

        if (checkCollision()) {
            birdY = height / 2f
            birdVelocity = 5f
        }

        postInvalidateOnAnimation()
    }

    private fun checkCollision(): Boolean {
        return pipes.any { pipe ->
            100f in pipe.x..(pipe.x + pipeWidth) && (birdY <= pipe.bottom || birdY >= pipe.top)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            birdVelocity = -jumpVelocity
        }
        return true
    }

    data class Pipe(var x: Float, var top: Float, var bottom: Float)
}
