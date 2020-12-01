package com.example.myapplication.Shape;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;

import java.util.List;

import androidx.annotation.ColorInt;

public class IShape {
    int mType;
    public IShape(){
        mType = getType();
    }
    //Paint mPaint;
    int mColor;
    float mPaintWidth;
    RectF mRectF;
    PointF mPoint1,mPoint2;
    List<PointF> pointFList;
    public RectF getLocation(){return null;};
    public Bitmap draw(Bitmap srcBitmap){return null;};

    public void copy(IShape shape) {
        mType = shape.mType;
        //mPaint = shape.mPaint;
        mColor = shape.mColor;
        mPaintWidth = shape.mPaintWidth;
        mRectF = shape.mRectF;
        mPoint1 = shape.mPoint1;
        mPoint2 = shape.mPoint2;
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
        }else if(shape.mType == LineShape.sShapeType){
            newShape = new LineShape();
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
