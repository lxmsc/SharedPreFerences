package com.example.myapplication.Canvas;

import android.graphics.Bitmap;

import com.example.myapplication.Shape.IShape;

import java.util.ArrayList;
import java.util.List;

public class Canvas {
    public Bitmap mCanvas;
    public List<IShape> mShapes;

    public Canvas(int width,int height){
        mCanvas = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        mShapes = new ArrayList<>();
    }
    public Canvas(Canvas canvas){
        mCanvas = Bitmap.createBitmap(canvas.mCanvas);
        mShapes = new ArrayList<>(canvas.mShapes);
    }
    public void recycler(){
        mCanvas.recycle();
    }
}
