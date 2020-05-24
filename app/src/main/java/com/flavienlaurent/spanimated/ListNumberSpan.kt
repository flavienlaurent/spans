package com.flavienlaurent.spanimated

import android.content.Context
import android.graphics.*
import android.graphics.Paint.FontMetricsInt
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.RequiresApi
import android.text.TextPaint
import android.text.style.ReplacementSpan
import com.flavienlaurent.spanimated.utils.SpanDimensionUtils

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class ListNumberSpan(var isCornerGray: Boolean, var cornerText:String =  "",
                     var context: Context) : ReplacementSpan() {

    var backGroundColor: Int = Color.BLACK
    var textColor: Int = Color.WHITE

    private var mBackgroundPaint: Paint = Paint()
    private var cornerTextPaint: Paint = TextPaint()
    private var mWidth = -1
    private val mRectF = RectF()


    init {
        initPaint()
    }


    private fun initPaint() {
        mBackgroundPaint.color = backGroundColor
        mBackgroundPaint.isAntiAlias = true

        cornerTextPaint.color = textColor
        cornerTextPaint.isAntiAlias = true
        cornerTextPaint.textSize = SpanDimensionUtils.sp2px(context, 9f).toFloat()
        cornerTextPaint.textAlign = Paint.Align.CENTER
    }

    override fun getSize(paint: Paint, text: CharSequence, start: Int, end: Int, fm: FontMetricsInt?): Int { //return text with relative to the Paint
        mWidth = paint.measureText(text, start, end).toInt()
        return mWidth
    }

    override fun draw(canvas: Canvas, text: CharSequence, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
        val charWidth = paint.measureText(cornerText)

        canvas.save()
        canvas.translate(0f , (bottom-top) / 8f )
        mRectF.set(x, top.toFloat(), x + charWidth, bottom.toFloat())
        canvas.drawRoundRect(mRectF, mRectF.height() / 2f, mRectF.height() / 2f, mBackgroundPaint)
        canvas.restore()

        canvas.save()
        canvas.translate(mWidth / 2f , 0f)
        canvas.drawText(cornerText, 0, cornerText.length, x, y.toFloat(), cornerTextPaint)
        canvas.restore()

    }

}