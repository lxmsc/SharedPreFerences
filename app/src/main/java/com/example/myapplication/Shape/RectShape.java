package com.example.myapplication.Shape;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

public class RectShape extends IShape {
    public static final int sShapeType = 1;

    public RectShape(){}
    public RectShape(RectF rectF){
        this(rectF,0xFF000000);
    }

    public RectShape(@Nullable RectF rectF,@Nullable int color){
        mRectF = rectF;
        mColor = color;
    }
    @Override
    public RectF getLocation() {
        return null;
    }

    @Override
    public Bitmap draw(Bitmap srcBitmap) {
        if(srcBitmap == null){
            return srcBitmap;
        }
        Canvas canvas = new Canvas(srcBitmap);
        Paint paint = new Paint();
        paint.setColor(mColor);
        canvas.drawRect(mRectF,paint);
        return srcBitmap;
    }
    public static class Builder implements IShapeBuider{

        @Override
        public IShape buildShape(RectF rectF, int color) {
            return new RectShape(rectF,color);
        }
    }

    @Override
    public int getType() {
        return sShapeType;
    }
}
