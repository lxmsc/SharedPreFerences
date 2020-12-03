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
    public LineShape(@Nullable PointF point1,@Nullable PointF point2,@Nullable int color,@Nullable float paintWidth){
        mPoint1=point1;
        mPoint2=point2;
        mColor = color;
        mPaintWidth = paintWidth;
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
        paint.setStrokeWidth(mPaintWidth);
        paint.setColor(mColor);
        canvas.drawLine(mPoint1.x,mPoint1.y,mPoint2.x,mPoint2.y,paint);
        return srcBitmap;
    }
    public static class Builder implements IShapeBuider{

        @Override
        public IShape buildShape() {
            return new LineShape();
        }

        @Override
        public IShape buildShape(RectF rectF, int color, float paintWidth) {
            return null;
        }

        @Override
        public IShape buildShape(PointF point1, PointF point2, int color, float paintWidth) {
            return new LineShape(point1,point2,color,paintWidth);
        }

    }

    @Override
    public int getType() {
        return sShapeType;
    }
}
