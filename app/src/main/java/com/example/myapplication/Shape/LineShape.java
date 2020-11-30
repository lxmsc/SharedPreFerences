package com.example.myapplication.Shape;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;

import androidx.annotation.Nullable;

public class LineShape extends IShape {
    public static final int sShapeType = 3;

    public LineShape(){}
    public LineShape(@Nullable RectF rectF,@Nullable Paint paint){
        mRectF = rectF;
        mPaint = paint;
    }
    public LineShape(@Nullable PointF point1,@Nullable PointF point2,@Nullable Paint paint){
        mPoint1=point1;
        mPoint2=point2;
        mPaint = paint;
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
        canvas.drawLine(mPoint1.x,mPoint1.y,mPoint2.x,mPoint2.y,mPaint);
        return srcBitmap;
    }
    public static class Builder implements IShapeBuider{
        @Override
        public IShape buildShape(RectF rectF, Paint paint) {
            return new LineShape(rectF,paint);
        }

        @Override
        public IShape buildShape(PointF point1, PointF point2, Paint paint) {
            return new LineShape(point1,point2,paint);
        }
    }

    @Override
    public int getType() {
        return sShapeType;
    }
}
