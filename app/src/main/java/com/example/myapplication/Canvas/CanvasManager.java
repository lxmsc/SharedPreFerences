package com.example.myapplication.Canvas;

import android.graphics.Bitmap;

import com.example.myapplication.Shape.IShape;
import com.example.myapplication.util.PermissionUtil;
import com.example.myapplication.util.SaveGson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class CanvasManager {
    private Canvas mCanvas;
    private CanvasBuider mBuider;
    private Canvas mCacheMap;
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
    public void setLastListShape(){
        SaveGson.save(sListShapeKey,mCanvas.mShapes,new TypeToken<List<IShape>>(){}.getType());
    }
    public void drawCache(IShape iShape){
        if(mCacheMap == null){
            mCacheMap = new Canvas(mCanvas);
        }
        mCacheMap.mShapes.add(iShape);
        iShape.draw(mCacheMap.mCanvas);
    }

    public void saveCanvas(){
        if(mCacheMap==null){
            return;
        }
        mCanvas.recycler();
        mCanvas = mCacheMap;
        mCacheMap = null;
    }
    public Bitmap getCurrantCanvas(){
        return mCacheMap == null?mCanvas.mCanvas:mCacheMap.mCanvas;
    }
    public void clearCache(){
        if(mCacheMap != null){
            mCacheMap = null;
        }
    }
    public void onDestroy(){
        mCanvas= null;
    }

}
