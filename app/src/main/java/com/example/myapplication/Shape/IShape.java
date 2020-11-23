package com.example.myapplication.Shape;

import android.graphics.Bitmap;
import android.graphics.RectF;

import androidx.annotation.ColorInt;

public class IShape {
    int mType;
    public IShape(){
        mType = getType();
    }
    @ColorInt
    int mColor;
    RectF mRectF;
    public RectF getLocation(){return null;};
    public Bitmap draw(Bitmap srcBitmap){return null;};

    public void copy(IShape shape) {
        mType = shape.mType;
        mColor = shape.mColor;
        mRectF = shape.mRectF;
    }

    public static IShape getRawShape(IShape shape) {
        if (shape == null) {
            return null;
        }
        IShape newShape = null;
        if (shape.mType == OvalShape.sShapeType) {
            newShape = new OvalShape();
        } else if (shape.mType == RectShape.sShapeType){
            newShape = new RectShape();
        }
        if (newShape != null) {
            newShape.copy(shape);
        }
        return newShape;
    }

    public int getType() {
        return 0;
    }
}
