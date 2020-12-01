package com.example.myapplication.Shape;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

public class OvalShape extends IShape {

    public static final int sShapeType = 2;

    public OvalShape(){
        super();
    }

    public OvalShape(@Nullable RectF rectF, @Nullable int color, float paintWidth){
        super();
        mRectF = rectF;
        mColor = color;
        mPaintWidth = paintWidth;
    }

    @Override
    public int getType() {
        return sShapeType;
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
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(mColor);
        paint.setStrokeWidth(mPaintWidth);
        canvas.drawOval(mRectF,paint);
        return srcBitmap;
    }
    public static class Builder implements IShapeBuider{

        @Override
        public IShape buildShape(RectF rectF, int color, float paintWidth) {
            return new OvalShape(rectF,color,paintWidth);
        }

        @Override
        public IShape buildShape(PointF point1, PointF point2, int color, float paintWidth) {
            RectF rectF = new RectF();
            rectF.top = Math.min(point1.y,point2.y);
            rectF.bottom = Math.max(point1.y,point2.y);
            rectF.left = Math.min(point1.x,point2.x);
            rectF.right = Math.max(point1.x,point2.x);
            return new OvalShape(rectF,color,paintWidth);
        }
    }
}
