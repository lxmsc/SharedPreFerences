package com.example.myapplication.Shape;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;

public interface IShapeBuider {

    IShape buildShape(RectF rectF,int color);
    IShape buildShape(PointF point1, PointF point2, int color);
}
