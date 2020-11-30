package mvc;

import android.graphics.Paint;

import com.example.myapplication.Canvas.CanvasManager;
import com.example.myapplication.Shape.IShape;
import com.example.myapplication.Shape.IShapeBuider;
import com.example.myapplication.Shape.OvalShape;
import com.example.myapplication.util.SaveGson;

public class CurrentDate {

    private CanvasManager mCanvasManger;
    private IShapeBuider mShape;
    private Paint mPaint;
    private String sColorKey = "colorkey";

    public CurrentDate(){
        mShape = new OvalShape.Builder();
        mPaint = new Paint();
        if(SaveGson.get(sColorKey, int.class)==null){
            setPaintColor(0xFFFFFFFF);
        }
        else{
            setPaintColor(SaveGson.get(sColorKey,int.class));
        }
        setPaintStyle(Paint.Style.STROKE);
        setPaintStrokeWidth(1);
    }

    public CurrentDate(IShapeBuider iShapeBuider,Paint paint){
        mShape = iShapeBuider;
        mPaint = paint;
    }
    public IShapeBuider getmShape() {
        return mShape;
    }

    public void setmShape(IShapeBuider mShape) {
        this.mShape = mShape;
    }

    public Paint getmPaint() {
        return mPaint;
    }

    public void setmPaint(Paint mPaint) {
        this.mPaint = mPaint;
    }

    public void setPaintColor(int color){
        mPaint.setColor(color);
    }

    public int getPaintColor(){
        return mPaint.getColor();
    }

    public void setPaintStrokeWidth(float width){
        mPaint.setStrokeWidth(width);
    }

    public void setPaintStyle(Paint.Style style){
        mPaint.setStyle(style);
    }
}
