package com.example.myapplication.popup;

import com.example.myapplication.Shape.IShapeBuider;
import com.example.myapplication.Shape.OvalShape;

import mvc.IData;

public class PopShapeData implements IData {
    public IShapeBuider iShapeBuider;
    public PopShapeData(){iShapeBuider = new OvalShape.Builder(); };
    public PopShapeData(IShapeBuider shapeBuider) {
        iShapeBuider = shapeBuider;
    }
}
