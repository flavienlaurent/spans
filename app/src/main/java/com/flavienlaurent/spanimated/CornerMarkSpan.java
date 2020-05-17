package com.flavienlaurent.spanimated;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ReplacementSpan;

/**
 */
public class CornerMarkSpan extends ReplacementSpan {

    private final int cornerHeight;
    private final int cornerWidth;
    private TextPaint mCornerPaint;
    private Paint mTextPaint;
    private int mWidth;
    private String text = "";

    private Drawable cornerDrawable;
    private Rect mCornerBound;
    private Context mContext;

    public CornerMarkSpan(Context context, Paint textPaint) {
        mContext = context;
        mTextPaint = textPaint;

        mCornerPaint = new TextPaint();
        mCornerPaint.setStyle(Paint.Style.FILL);
        mCornerPaint.setColor(Color.WHITE);
        mCornerPaint.setTextSize(mTextPaint.getTextSize() * 0.5f);
        mCornerPaint.setAntiAlias(true);

        cornerDrawable = context.getResources().getDrawable(R.drawable.corner_drawbale);

        cornerHeight = dp2px(context, 15);
        cornerWidth = dp2px(context, 15);

        mCornerBound = new Rect(0, 0, cornerWidth, cornerHeight);
        cornerDrawable.setBounds(mCornerBound);
    }

    /**
     * dp转px
     */
    public static int dp2px(Context context, float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density + 0.5f);
    }


    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        //return text with relative to the Paint
		mWidth = (int) paint.measureText(text, start, end);
        if (text == null) {
            text = "";
        }
		this.text = text.toString();
        return mWidth;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        //draw the frame with custom Paint
//        float halfHeight = (bottom - top) / 2f;
//        canvas.drawCircle(x, top + halfHeight, halfHeight, mPaint);
        if (!TextUtils.isEmpty(this.text)) {
            canvas.drawText(text, start, end, x, y, mTextPaint);
        }

        canvas.save();
        canvas.translate(x, top - cornerHeight);
        cornerDrawable.draw(canvas);
        canvas.restore();

        canvas.save();
        canvas.translate(0, -cornerHeight - dp2px(mContext, 4));
        canvas.drawText("12", 0, 2, x, y, mCornerPaint);
        canvas.restore();


//        canvas.drawRect(x, top, x + mWidth, bottom, mPaint);
    }
}
