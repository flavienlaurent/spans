package com.flavienlaurent.spanimated

import android.content.Context
import android.graphics.*
import android.graphics.Paint.FontMetricsInt
import android.os.Build
import android.support.annotation.RequiresApi
import android.text.TextPaint
import android.text.style.ReplacementSpan
import com.flavienlaurent.spanimated.utils.SystemUtils

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class ListNumberSpan(var isGrayStatus: Boolean, var number:String =  "",
                     var context: Context) : ReplacementSpan() {

    private val COLOR_SENTENCE_HIGH_LIGHT = Color.parseColor("#66F5FF9A")
    private val UNSELECT_SENTENCE_BACKGROUND_COLOR = Color.parseColor("#08000000")

    private val backGroundColor: Int = Color.parseColor("#262626")
    private val backGroundGrayColor: Int = Color.parseColor("#A7A7A7")
    private val textColor: Int = Color.WHITE

    /**
     * 圆角矩形 和 数字  底部重叠后，将圆角矩形往Y轴的偏移量
     */
    private val numberBaseLineYDelta = SystemUtils.dp2px(context, 3f)
    private var mNumberWidth = 0

    private val numberPaint: Paint = TextPaint()
    private val mRectBackgroundPaint: Paint = Paint()
    private val mRoundRectBackgroundPaint: Paint = Paint()


    private val mRoundRectF = RectF()
    private val roundRectRadius: Float = SystemUtils.dp2px(context, 6f).toFloat()

    /**
     * 排除数字后的宽度
     */
    private val roundRectBackgroundWidth = SystemUtils.dp2px(context, 12f)
    private val roundRectBackgroundHeight = SystemUtils.dp2px(context, 12f)

    init {
        initPaint()
    }


    private fun initPaint() {
        mRectBackgroundPaint.color  = if (isGrayStatus) UNSELECT_SENTENCE_BACKGROUND_COLOR else COLOR_SENTENCE_HIGH_LIGHT
        mRectBackgroundPaint.style = Paint.Style.FILL

        mRoundRectBackgroundPaint.color = if(isGrayStatus) backGroundGrayColor else backGroundColor
        mRoundRectBackgroundPaint.isAntiAlias = true

        numberPaint.color = textColor
        numberPaint.isAntiAlias = true
        numberPaint.textSize = SystemUtils.sp2px(context, 9f).toFloat()
        numberPaint.textAlign = Paint.Align.CENTER
    }

    override fun getSize(paint: Paint, text: CharSequence, start: Int, end: Int, fm: FontMetricsInt?): Int { //return text with relative to the Paint
        mNumberWidth = numberPaint.measureText(text, start, end).toInt()
        mNumberWidth += roundRectBackgroundWidth

        return mNumberWidth
    }

    override fun draw(canvas: Canvas, text: CharSequence, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
        canvas.drawRect(x, top.toFloat(), x + mNumberWidth, bottom.toFloat(), mRectBackgroundPaint)
        val deltaY = (y - top - roundRectBackgroundHeight + numberBaseLineYDelta).toFloat()

        canvas.save()
        canvas.translate(0f, (-numberBaseLineYDelta).toFloat())

        canvas.save()
        canvas.translate(0f, deltaY)
        mRoundRectF.set(x, top.toFloat(), x + mNumberWidth, top.toFloat() + roundRectBackgroundHeight)
        canvas.drawRoundRect(mRoundRectF, roundRectRadius, roundRectRadius, mRoundRectBackgroundPaint)
        canvas.restore()

        canvas.save()
        canvas.translate(mNumberWidth / 2f ,0f )
        canvas.drawText(number, 0, number.length, x, y.toFloat(), numberPaint)
        canvas.restore()

        canvas.restore()

    }

}