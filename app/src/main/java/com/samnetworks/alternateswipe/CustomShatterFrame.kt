package com.samnetworks.alternateswipe

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import java.util.*

/**
 * Created by Sourabh Gupta on 29/6/20.
 */
class CustomShatterFrame @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private var pieces: Array<Piece?>? = null
    private var fraction: Float = 0.0f
    private var mBitmap: Bitmap? = null
    var animationRunning: Boolean = false
    fun advance(animatedFraction: Float) {
        if (pieces == null || pieces?.size == 0) {
            buildPieces()
        }
        fraction = animatedFraction
        if (fraction < -0.003f && fraction != -1.0f) {
            animationRunning = true
            if (getChildAt(0) != null) {
                getChildAt(0).visibility = View.INVISIBLE
            }
        } else if (fraction == -1.0f) {
            if (getChildAt(0) != null) {
                getChildAt(0).visibility = View.VISIBLE
            }
            animationRunning = false
        } else {
            if (getChildAt(0) != null) {
                getChildAt(0).visibility = View.VISIBLE
            }
            animationRunning = false
        }
        invalidate()
    }

    /**
     * Build the final bitmap-pieces to draw in animation
     */
    private fun buildPieces() {
        mBitmap = Utils.convertViewToBitmap(this)
        if (mBitmap == null) return
        val pathArrayList: MutableList<Path> = ArrayList()
        val dm = DisplayMetrics()
        (context as AppCompatActivity).getWindowManager().getDefaultDisplay().getMetrics(dm)
        Utils.screenWidth = dm.widthPixels
        Utils.screenHeight = dm.heightPixels
        run {
            var i = 0
            while (i < Utils.screenWidth) {
                var j = 0
                while (j < Utils.screenHeight) {
                    val path = Path()
                    path.addRect(
                        i.toFloat(),
                        j.toFloat(),
                        i + 100.toFloat(),
                        j + 100.toFloat(),
                        Path.Direction.CW
                    )
                    pathArrayList.add(path)
                    j += 100
                }
                i += 100
            }
        }
        pieces = arrayOfNulls<Piece?>(pathArrayList.size)
        val paint = Paint()
        val matrix = Matrix()
        val canvas = Canvas()
        for (i in pieces!!.indices) {
            val shadow = Utils.nextInt(
                0,
                3
            )
            val path = pathArrayList[i]
            val r = RectF()
            path.computeBounds(r, true)
            val pBitmap = Utils.createBitmapSafely(
                r.width().toInt() + shadow * 2,
                r.height().toInt() + shadow * 2, Bitmap.Config.ARGB_8888, 1
            )
            if (pBitmap == null) {
                pieces!![i] = Piece(-1, -1,60, null, shadow)
                continue
            }
            pieces!![i] = Piece(
                r.left.toInt() - shadow,
                r.top.toInt() - shadow,60, pBitmap, shadow
            )
            canvas.setBitmap(pieces?.get(i)?.bitmap)
            val mBitmapShader = BitmapShader(
                mBitmap!!,
                Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP
            )
            matrix.reset()
            matrix.setTranslate(-r.left + shadow, -r.top + shadow)
            mBitmapShader.setLocalMatrix(matrix)
            paint.reset()
            val offsetPath = Path()
            offsetPath.addPath(path, -r.left + shadow, -r.top + shadow)

            // Draw shadow
            paint.style = Paint.Style.FILL
            paint.setShadowLayer(shadow.toFloat(), 0f, 0f, -0xcccccd)
            canvas.drawPath(offsetPath, paint)
            paint.setShadowLayer(0f, 0f, 0f, 0)

            // In case the view has alpha channel
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.XOR)
            canvas.drawPath(offsetPath, paint)
            paint.xfermode = null

            // Draw bitmap
            paint.shader = mBitmapShader
            canvas.drawPath(offsetPath, paint)
        }
        Arrays.sort(pieces)
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        val fraction: Float = fraction
        var piecesNum: Int = pieces?.size ?: 0
        if (pieces != null && animationRunning) {
            for (p in pieces!!) {
                if (p?.bitmap != null && p.advance(fraction)) canvas?.drawBitmap(
                    p.bitmap,
                    p.matrix,
                    null
                ) else piecesNum--
            }
        } else {
            if (getChildAt(0) != null) {
                getChildAt(0).visibility = View.VISIBLE
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if(pieces!=null) {
            for (piece in pieces!!) {
                piece?.bitmap?.recycle()
            }
            pieces = null
        }

    }
}