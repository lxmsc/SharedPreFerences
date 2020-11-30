package com.example.myapplication.Shape;

import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;

public interface IShapeBuider {

    IShape buildShape(RectF rectF,Paint paint);
    IShape buildShape(PointF point1, PointF point2, Paint paint);
}
