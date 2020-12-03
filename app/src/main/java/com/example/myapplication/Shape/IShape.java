package com.example.myapplication.Shape;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.net.wifi.aware.DiscoverySession;

import java.util.List;

import androidx.annotation.ColorInt;

public class IShape {
    int mType;
    public IShape(){
        mType = getType();
    }
    public IShape(IShape iShape){
        mType = getType();
        mColor = iShape.mColor;
        mPaintWidth = iShape.mPaintWidth;
        mRectF = new RectF(iShape.mRectF);
        mPoint1 = iShape.mPoint1;
        mPoint2 = iShape.mPoint2;

    }
    public IShape addPoint(PointF pointF){
        IShape iShape = new IShape();
        iShape.copy(this);
        if(iShape.mRectF!=null) {
            iShape.mRectF.bottom += pointF.y;
            iShape.mRectF.top += pointF.y;
            iShape.mRectF.left += pointF.x;
            iShape.mRectF.right += pointF.x;
        }
        iShape = getRawShape(iShape);
        return iShape;
    }
    //Paint mPaint;
    int mColor;
    float mPaintWidth;
    RectF mRectF;
    PointF mPoint1,mPoint2;
    public RectF getLocation(){return null;};
    public Bitmap draw(Bitmap srcBitmap){return null;};

    public boolean isIn(PointF pointF){
        if(mRectF!=null){
            if(mRectF.left<pointF.x&&pointF.x<mRectF.right&&mRectF.top<pointF.y&&pointF.y<mRectF.bottom){
                return true;
            }
            return false;
        }
        int flag=0;
        if(mPoint1.x<pointF.x&&pointF.x<mPoint2.x){
            flag+=1;
        }
        if(mPoint2.x<pointF.x&&pointF.x<mPoint1.x){
            flag+=1;
        }
        if(mPoint1.y<pointF.y&&pointF.y<mPoint2.y){
            flag+=1;
        }
        if(mPoint2.y<pointF.y&&pointF.y<mPoint1.y){
            flag+=1;
        }
        if(flag==2){
            return true;
        }
        return false;
    }
    public void copy(IShape shape) {
        mType = shape.mType;
        mColor = shape.mColor;
        mPaintWidth = shape.mPaintWidth;
        mRectF = new RectF(shape.mRectF);
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
        }else if(shape.mType == RoundShape.sShapeType){
            newShape = new RoundShape();
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
