package com.lovejjfg.indexdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class IndexBar extends View {

    private Paint mPaint;
    private static final String[] LETTERS = new String[]{"A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y",
            "Z"};
    private static final int[] PRESSED_STATE = new int[]{android.R.attr.state_pressed};
    private static final int[] STATE_FOCUSED = new int[]{android.R.attr.state_focused};
    private static final String TAG = IndexBar.class.getSimpleName();
    private int mHeight;
    private int mCellWidth;
    private float mCellHeight;
    private Rect mRect;
    private boolean pressed;
    private MotionEvent mMotionEvent;


    public IndexBar(Context context) {
        this(context, null);
    }

    public IndexBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setOnClickListener(null);
        // mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(dp2px(14));
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mRect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < LETTERS.length; i++) {
            String text = LETTERS[i];
            float textWidth = mPaint.measureText(text);
            mPaint.getTextBounds(text, 0, text.length(), mRect);
            float textHeight = mRect.height();
            float x = mCellWidth * 0.5f - textWidth * 0.5f;
            float y = mCellHeight * 0.5f + textHeight * 0.5f + mCellHeight * i;
            mPaint.setColor(mIndex == i ? Color.BLUE : Color.WHITE);
            canvas.drawText(text, x, y, mPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = getMeasuredHeight();
        mCellWidth = getMeasuredWidth();
        mCellHeight = mHeight * 1.0f / LETTERS.length;
    }

    private int mIndex = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
        mMotionEvent = MotionEvent.obtain(event);
//        }
        return true;
    }

    @Override
    public boolean performClick() {
        if (mMotionEvent == null) return super.performClick();

        float y;
        int currentIndex;
        switch (mMotionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                y = mMotionEvent.getY();
                // 根据触摸到的位置, 获取索引
                currentIndex = (int) (y / mCellHeight);
                if (currentIndex != mIndex) {
                    if (mOnLetterChangeListener != null) {
                        if (currentIndex < LETTERS.length) {
                            mOnLetterChangeListener.onLetterChange(LETTERS[currentIndex]);
                        }
                    }
                    mIndex = currentIndex;
                }
                // Log.i(TAG, "letter: "+LETTERS[currentIndex]);
                break;
            case MotionEvent.ACTION_MOVE:
                y = mMotionEvent.getY();
                currentIndex = (int) (y / mCellHeight);
                if (currentIndex != mIndex) {
                    if (mOnLetterChangeListener != null) {
                        if (currentIndex < LETTERS.length) {
                            mOnLetterChangeListener.onLetterChange(LETTERS[currentIndex]);
                        }
                    }

                    mIndex = currentIndex;
                }
                // Log.i(TAG, "letter: "+LETTERS[currentIndex]);
                break;
            case MotionEvent.ACTION_UP:
                mIndex = -1;
                break;

            default:
                break;
        }
        return true;
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        int[] states = super.onCreateDrawableState(extraSpace + 1);
        if (pressed) {
            mergeDrawableStates(states, STATE_FOCUSED);
        }
        return states;
    }

    public int dp2px(int dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    public interface OnLetterChangeListener {
        void onLetterChange(String letter);
    }

    private OnLetterChangeListener mOnLetterChangeListener;

    public OnLetterChangeListener getOnLetterChangeListener() {
        return mOnLetterChangeListener;
    }

    public void setOnLetterChangeListener(OnLetterChangeListener onLetterChangeListener) {
        mOnLetterChangeListener = onLetterChangeListener;
    }

}
