package com.example.myapplication.Canvas;

import android.graphics.Canvas;

public class CanvasBuider {
    private int mWidth;
    private int mHeight;
    public CanvasBuider setWidth(int width){
        mWidth = width;
        return this;
    }
    public CanvasBuider setHeight(int height){
        mHeight = height;
        return this;
    }
    public int getmWidth(){
        return mWidth;
    }
    public int getmHeight(){
        return mHeight;
    }
    public CanvasManager build(){
        return new CanvasManager(this);
    }
}
