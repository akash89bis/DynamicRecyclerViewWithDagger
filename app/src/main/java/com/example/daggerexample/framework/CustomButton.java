package com.example.daggerexample.framework;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.daggerexample.R;

public class CustomButton extends RelativeLayout {

    private LayoutInflater mInflater;
    private String mText;
    private int mColor;
    private OnClickListener listener;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_UP) {
            if(listener != null) listener.onClick(this);
        }
        return super.dispatchTouchEvent(event);
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_UP && (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER || event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            if(listener != null) listener.onClick(this);
        }
        return super.dispatchKeyEvent(event);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public CustomButton(Context context) {
        super(context);
        mInflater = LayoutInflater.from(context);
        readAttrs(context, null);
        init(context, null);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
        readAttrs(context, attrs);
        init(context, attrs);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater = LayoutInflater.from(context);
        readAttrs(context, attrs);
        init(context, attrs);
    }

    @SuppressLint("ResourceAsColor")
    private void readAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomButton, 0, 0);
        mText = a.getString(R.styleable.CustomButton_buttonText);
        mColor = a.getColor(R.styleable.CustomButton_buttonTextColor, R.color.colorBlack);
        // Important: always recycle the TypedArray
        a.recycle();
    }


    private void init(Context context, AttributeSet attrs) {
        View v = mInflater.inflate(R.layout.custom_button, this, true);
        Button btn = v.findViewById(R.id.button);
        btn.setText(mText);
        btn.setTextColor(mColor);
    }
}
