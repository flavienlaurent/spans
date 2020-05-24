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

    private var cornerDrawable: Drawable?
    private var mCornerBound: Rect = Rect(0,0, 0,0)
    private var cornerHeight: Int
    private var cornerWidth: Int

    init {
        initPaint()
        cornerWidth = SpanDimensionUtils.dp2px(context, 17f)
        cornerHeight = SpanDimensionUtils.dp2px(context, 16f)

        val drawableRes = if(isCornerGray) R.drawable.default_corner_back_one_digital else R.drawable.corner_back_one_digital
        cornerDrawable = context.getDrawable(drawableRes)
        updateBackgroundBound(cornerWidth, cornerHeight)
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

        cornerWidth = mWidth
        updateBackgroundBound(cornerWidth, cornerHeight)

        return mWidth
    }

    private fun updateBackgroundBound(right: Int, bottom: Int) {
        mCornerBound.right = right
        mCornerBound.bottom = bottom
        cornerDrawable?.bounds = mCornerBound
    }

    override fun draw(canvas: Canvas, text: CharSequence, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
        val charWidth = paint.measureText(cornerText)
//        mRectF.set(x, top.toFloat(), x + charWidth, bottom.toFloat())
//        canvas.drawOval(mRectF, mBackgroundPaint)


        canvas.save()
        canvas.translate(x, top.toFloat() + cornerHeight/3f)
        cornerDrawable?.draw(canvas)
        canvas.restore()


        canvas.save()
        canvas.translate(cornerWidth / 2f - 2, 0f)
        canvas.drawText(cornerText, 0, cornerText.length, x, y.toFloat(), cornerTextPaint)
        canvas.restore()

    }

}