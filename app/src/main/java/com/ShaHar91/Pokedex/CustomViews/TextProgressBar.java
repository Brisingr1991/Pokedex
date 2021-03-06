package com.ShaHar91.Pokedex.CustomViews;


import com.ShaHar91.Pokedex.R.styleable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class TextProgressBar extends ProgressBar {

    private String text = "HP";
    private int textColor = Color.BLACK;
    private float textSize = 15;

    public TextProgressBar(Context context) {
        super(context);
    }

    public TextProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttrs(attrs);
    }

    public TextProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setAttrs(attrs);
    }

    public void setAttrs(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs,
                    styleable.TextProgressBar, 0, 0);
            setText(a.getString(styleable.TextProgressBar_text));
            setTextColor(a.getColor(styleable.TextProgressBar_textColor,
                    Color.BLACK));
            setTextSize(a
                    .getDimension(styleable.TextProgressBar_textSize, 18));
            a.recycle();
        }
    }

    @Override
    public synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        Rect bounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), bounds);
        int x = getWidth() / 2 - bounds.centerX();
        int y = getHeight() / 2 - bounds.centerY();
        canvas.drawText(text, x, y, textPaint);
    }

    public String getText() {
        return text;
    }

    public synchronized void setText(String text) {
        if (text != null) {
            this.text = text;
        } else {
            this.text = "";
        }
        postInvalidate();
    }

    public int getTextColor() {
        return textColor;
    }

    public synchronized void setTextColor(int textColor) {
        this.textColor = textColor;
        postInvalidate();
    }

    public float getTextSize() {
        return textSize;
    }

    public synchronized void setTextSize(float textSize) {
        this.textSize = textSize;
        postInvalidate();
    }
}