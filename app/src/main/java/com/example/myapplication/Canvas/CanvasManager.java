package com.example.myapplication.Canvas;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.util.Log;

import com.example.myapplication.Shape.IShape;
import com.example.myapplication.Shape.LineShape;
import com.example.myapplication.util.SaveGson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class CanvasManager {
    private Canvas mCanvas;
    private CanvasBuider mBuider;
    private Canvas mCacheCanvas;
    public CanvasManager(CanvasBuider canvasBuider){
        mBuider = canvasBuider;
    }
    private static final String sListShapeKey = "ListShape";
    public void onCreate(){
        mCanvas = new Canvas(mBuider.getmWidth(),mBuider.getmHeight());
    }
    public void getLastListShape(){
        List<IShape> iShapeList = SaveGson.get(sListShapeKey, new TypeToken<List<IShape>>(){}.getType());
        if(iShapeList == null){
            return;
        }
        for(IShape iShape : iShapeList){
            iShape = IShape.getRawShape(iShape);
            drawCache(iShape);
        }
        saveCanvas();
    }
    public IShape getChoseShape(PointF pointF){
        List<IShape> iShapeList = SaveGson.get(sListShapeKey, new TypeToken<List<IShape>>(){}.getType());
        for(IShape iShape : iShapeList){
            if(iShape.isIn(pointF)){
                iShapeList.remove(iShape);
                clearCache();
                onClear();
                for(IShape iShape1 : iShapeList){
                    iShape1 = IShape.getRawShape(iShape1);
                    drawCache(iShape1);
                }
                saveCanvas();
                setLastListShape();
                return iShape;
            }
        }
        return null;
    }

    public void setLastListShape(){
        SaveGson.save(sListShapeKey,mCanvas.mShapes,new TypeToken<List<IShape>>(){}.getType());
    }
    public void drawCache(IShape iShape){
        if(mCacheCanvas == null){
            mCacheCanvas = new Canvas(mCanvas);
        }
        mCacheCanvas.mShapes.add(iShape);
        iShape.draw(mCacheCanvas.mCanvas);
    }

    public void saveCanvas(){
        if(mCacheCanvas == null){
            return;
        }
        mCanvas.recycler();
        mCanvas = mCacheCanvas;
        mCacheCanvas = null;
    }
    public Bitmap getCurrantCanvas(){
        return mCacheCanvas == null?mCanvas.mCanvas: mCacheCanvas.mCanvas;
    }
    public void clearCache(){
        if(mCacheCanvas != null){
            mCacheCanvas = null;
        }
    }
    public void onDestroy(){
        mCanvas = null;
    }
    public void onClear(){
        mCanvas = new Canvas(mBuider.getmWidth(),mBuider.getmHeight());

    }
}
