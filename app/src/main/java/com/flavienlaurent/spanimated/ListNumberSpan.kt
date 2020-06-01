package com.flavienlaurent.spanimated

import android.content.Context
import android.graphics.*
import android.graphics.Paint.FontMetricsInt
import android.os.Build
import android.support.annotation.RequiresApi
import android.text.TextPaint
import android.text.style.ReplacementSpan
import com.flavienlaurent.spanimated.utils.SystemUtils
import kotlin.math.abs

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class ListNumberSpan(var isCornerGray: Boolean, var cornerText:String =  "",
                     var context: Context) : ReplacementSpan() {
    val COLOR_SENTENCE_HIGH_LIGHT = Color.parseColor("#66F5FF9A")
    val UNSELECT_SENTENCE_BACKGROUND_COLOR = Color.parseColor("#08000000")
    var backGroundColor: Int = Color.parseColor("#262626")
    var backGroundGrayColor: Int = Color.parseColor("#A7A7A7")
    var textColor: Int = Color.WHITE

    private val mRectBackgroundPaint: Paint = Paint()
    private var mBackgroundPaint: Paint = Paint()
    private var cornerTextPaint: Paint = TextPaint()
    private var mWidth = -1
    private val mRectF = RectF()

    private var mCornerHeight = SystemUtils.dp2px(context, 12f)
    private var backgroundDeltaY = 0f

    init {
        initPaint()
    }


    private fun initPaint() {
        mRectBackgroundPaint.color  = if (isCornerGray) UNSELECT_SENTENCE_BACKGROUND_COLOR else COLOR_SENTENCE_HIGH_LIGHT
        mRectBackgroundPaint.style = Paint.Style.FILL

        mBackgroundPaint.color = if(isCornerGray) backGroundGrayColor else backGroundColor
        mBackgroundPaint.isAntiAlias = true

        cornerTextPaint.color = textColor
        cornerTextPaint.isAntiAlias = true
        cornerTextPaint.textSize = SystemUtils.sp2px(context, 9f).toFloat()
        cornerTextPaint.textAlign = Paint.Align.CENTER
    }

    override fun getSize(paint: Paint, text: CharSequence, start: Int, end: Int, fm: FontMetricsInt?): Int { //return text with relative to the Paint
        mWidth = paint.measureText(text, start, end).toInt()
        fm?.let {
            mCornerHeight = abs(fm.ascent)
            backgroundDeltaY = abs(fm.ascent - fm.top).toFloat()
        }

        return mWidth
    }

    override fun draw(canvas: Canvas, text: CharSequence, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
        val charWidth = paint.measureText(cornerText)

        canvas.drawRect(x, top.toFloat(), x + charWidth, bottom.toFloat(), mRectBackgroundPaint)

        canvas.save()
        // first line need extra delta y
        canvas.translate(0f , if (top < backgroundDeltaY ) (backgroundDeltaY * 2) else backgroundDeltaY)
        mRectF.set(x, top.toFloat(), x + charWidth, top.toFloat() + mCornerHeight)
        val radius = mCornerHeight / 2f
        canvas.drawRoundRect(mRectF, radius, radius, mBackgroundPaint)
        canvas.restore()

        canvas.save()
        canvas.translate(mWidth / 2f , -backgroundDeltaY)
        canvas.drawText(cornerText, 0, cornerText.length, x, y.toFloat(), cornerTextPaint)
        canvas.restore()

    }

}