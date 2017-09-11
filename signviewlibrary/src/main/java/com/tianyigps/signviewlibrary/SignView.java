package com.tianyigps.signviewlibrary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by cookiemouse on 2017/7/20.
 */

public class SignView extends View {

    private static final String TAG = "SignView";

    private Paint mPaint;
    private Path mPath;

    private Bitmap mBitmap;

    private boolean mIsNull = true;

    public SignView(Context context) {
        this(context, null);
    }

    public SignView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SignView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);  //抗锯齿
        mPaint.setDither(true);     //图像抖动处理
        //  mPaint.setMaskFilter();     //paint边缘处理
        mPaint.setColor(0xff222222);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (null != mBitmap) {
            canvas.drawBitmap(mBitmap, 0, 0, mPaint);
            mIsNull = false;
        }

        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mIsNull = false;
                mPath.moveTo(event.getX(), event.getY());
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                mIsNull = false;
                mPath.lineTo(event.getX(), event.getY());
                postInvalidate();
                break;
            }
            default: {
                Log.i(TAG, "onTouchEvent: default");
            }
        }
//        return super.onTouchEvent(event);
        return true;
    }

    //  清除图案
    public void clearPath() {
        mIsNull = true;
        mPath.reset();
        mBitmap = null;
        postInvalidate();
    }

    //  添加图象
    public void setBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
        postInvalidate();
    }

    //  保存图象
    public Bitmap getPic() {
        Bitmap bitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        this.draw(canvas);
        return bitmap;
    }

    //  返回是否为空
    public boolean isNull() {
        return mIsNull;
    }
}
