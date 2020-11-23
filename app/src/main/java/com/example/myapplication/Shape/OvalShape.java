package com.example.myapplication.Shape;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

public class OvalShape extends IShape {

    public static final int sShapeType = 2;

    public OvalShape(){
        super();
    }

    public OvalShape(RectF rectF){
        this(rectF,0xFF000000);
    }

    public OvalShape(@Nullable RectF rectF, @Nullable int color){
        super();
        mRectF = rectF;
        mColor = color;
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
        paint.setColor(mColor);
        canvas.drawOval(mRectF,paint);
        return srcBitmap;
    }
    public static class Builder implements IShapeBuider{

        @Override
        public IShape buildShape(RectF rectF, int color) {
            return new OvalShape(rectF,color);
        }
    }
}
