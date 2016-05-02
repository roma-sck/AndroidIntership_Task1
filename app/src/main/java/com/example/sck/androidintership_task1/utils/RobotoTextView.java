package com.example.sck.androidintership_task1.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class RobotoTextView extends TextView {

    public RobotoTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public RobotoTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RobotoTextView(Context context) {
        this(context, null, 0);
    }

    public void setTypeface(Typeface tf, int style) {
        if (style == Typeface.BOLD) {
            super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Bold.ttf"));
        } else if (style == Typeface.NORMAL) {
            super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Medium.ttf"));
        } else if (style == Typeface.ITALIC) {
            super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));
        }
    }
}
