package com.flavienlaurent.spanimated;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.LineBackgroundSpan;

import java.util.Arrays;

/**
 *
 */
public class LetterLineBackgroundSpan implements LineBackgroundSpan {

    private static final char[] sV = {'a','e','i','o','u','y'};

    private final Paint mCPaint;
    private final Paint mVPaint;
    private RectF mRectF = new RectF();

    public LetterLineBackgroundSpan() {
        mCPaint = new Paint();
        mCPaint.setColor(Color.MAGENTA);
        mCPaint.setAntiAlias(true);
        mVPaint = new Paint();
        mVPaint.setColor(Color.YELLOW);
        mVPaint.setAntiAlias(true);
    }

    @Override
    public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {
        float charx = left;
        for(int i = start ; i<end; i++) {
            String charAt = extractText(text, i, i + 1);
            float charWidth = p.measureText(charAt);
            mRectF.set(charx, top, charx += charWidth, bottom);
            if(Arrays.binarySearch(sV, charAt.charAt(0)) >= 0) {
                c.drawRect(mRectF, mVPaint);
            } else {
                c.drawRect(mRectF, mCPaint);
            }
        }
    }

    private String extractText(CharSequence text, int start, int end) {
        return text.subSequence(start, end).toString();
    }
}
