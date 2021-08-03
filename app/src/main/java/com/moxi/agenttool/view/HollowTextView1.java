package com.moxi.agenttool.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.moxi.agenttool.R;
import com.moxi.agenttool.utils.DisplayUtil;

/**
 * @ClassName: HollowTextView1
 * @Description: java类作用描述
 * @Author: join_lu
 * @CreateDate: 2021/7/27 17:10
 */
public class HollowTextView1 extends View{



    /**
     * A custom text view, can display text in transparent, just like hollow effect.
     * <p>For now, not support android:paddingTop and android:paddingBottom attributes! The text will
     * always drawn in vertical center</p>
     * Created by zjl on 16/3/22.
     */
        private static final String TAG = "HollowTextView";

        private Context mContext;

        // XML attributes
        private String mText;

        private int mTextSize = 15;
        private int mBgColor = 0x99ffffff;
        private int mCornerRadius = 0;
        private boolean mIsTopLeftRound = false;
        private boolean mIsTopRightRound = false;
        private boolean mIsBottomLeftRound = false;
        private boolean mIsBottomRightRound = false;
        private int mPaddingLeft = 0;
        private int mPaddingRight = 0;
        private int mPaddingTop = 0;
        private int mPaddingBottom = 0;

        // content bitmap paint config
        private Paint mTextPaint;
        private Paint mBgPaint;
        private Paint mCornerPaint;

        private Bitmap mContentBitmap;
        private int mWidth;
        private int mHeight;
        private Canvas bitMapCanvas;
        private RectF mBackgroundRect;

    public HollowTextView1(Context context) {
            super(context);
            mContext = context;
            init();
        }

        public HollowTextView1(Context context, AttributeSet attrs) {
            super(context, attrs);

            mContext = context;

            TypedArray a = mContext.getTheme().obtainStyledAttributes(attrs, R.styleable.HollowTextView,
                    0, 0);

            try {
                mText = a.getString(R.styleable.HollowTextView1_text);
                mTextSize = a.getDimensionPixelSize(R.styleable.HollowTextView1_textSize, mTextSize);
                mBgColor = a.getColor(R.styleable.HollowTextView1_bgColor, Color.WHITE);
                mCornerRadius = a.getDimensionPixelSize(R.styleable.HollowTextView1_cornerRadius,
                        mCornerRadius);
                mIsTopLeftRound = a.getBoolean(R.styleable.HollowTextView1_roundTopLeft, false);
                mIsTopRightRound = a.getBoolean(R.styleable.HollowTextView1_roundTopRight, false);
                mIsBottomLeftRound = a.getBoolean(R.styleable.HollowTextView1_roundBottomLeft, false);
                mIsBottomRightRound = a.getBoolean(R.styleable.HollowTextView1_roundBottomRight, false);
            } finally {
                a.recycle();
            }

            init();
        }

        private void init() {
            mBackgroundRect = new RectF();
            mTextPaint = new Paint();
            mTextPaint.setTextSize(DisplayUtil.sp2px(mContext, 200));
            mTextPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            mTextPaint.setAntiAlias(true);
            mTextPaint.setFakeBoldText(true);

            mBgPaint = new Paint();
            mBgPaint.setColor(mBgColor);
            mBgPaint.setAntiAlias(true);

            mCornerPaint = new Paint();
            mCornerPaint.setColor(mBgColor);
            mCornerPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));

        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

            mWidth = (int) mTextPaint.measureText(mText) + getPaddingLeft() + getPaddingRight();
            mHeight = mTextSize + getPaddingTop() + getPaddingBottom();

            mWidth = measureDimension(mWidth, widthMeasureSpec);
            mHeight = measureDimension(mHeight, heightMeasureSpec);

            setMeasuredDimension(mWidth, mHeight);
            Log.d(TAG, "----onMeasure---- the last width=" + mWidth + ", height=" + mHeight);
        }

        private int measureDimension(int defSize, int measureSpec) {
            int result = defSize;
            int specMode = MeasureSpec.getMode(measureSpec);
            int specSize = MeasureSpec.getSize(measureSpec);

            switch (specMode) {
                case MeasureSpec.UNSPECIFIED:
                    result = defSize;
                    break;
                case MeasureSpec.AT_MOST:
                    result = Math.min(defSize, specSize);
                    break;
                case MeasureSpec.EXACTLY:
                    result = specSize;
                    break;
            }
            return result;
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            Log.d(TAG, "----onSizeChanged----[w=" + w + ",h=" + h + ",oldw=" + oldw + ",oldh=" + oldh
                    + "]");

            mContentBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            bitMapCanvas = new Canvas(mContentBitmap);
            mBackgroundRect.set(0, 0, w, h);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            Log.d(TAG, "----onDraw----");

            if (!TextUtils.isEmpty(mText)) {
                drawContentBitmap();
                canvas.drawBitmap(mContentBitmap, 0, 0, null);
            }
        }

        private void drawContentBitmap() {
            Log.i(TAG, "----drawContentBitmap----");
            if (!TextUtils.isEmpty(mText)) {

                bitMapCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

                    bitMapCanvas.drawColor(mBgColor);

                // draw text in vertical center
                Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
                float x = mBackgroundRect.width() / 2 - mTextPaint.measureText(mText) / 2;
                float y = mBackgroundRect.top + mBackgroundRect.height() / 2 + mTextPaint.getTextSize()/2 ;
                bitMapCanvas.drawText(mText, x, y, mTextPaint);
            }
        }

        public String getText() {
            return mText;
        }

        public void setText(String text) {
            if (text == null || text.equals(mText)) {
                return;
            }

            this.mText = text;

            requestLayout();
            invalidate();
        }
    }