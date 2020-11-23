package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Canvas.CanvasBuider;
import com.example.myapplication.Canvas.CanvasManager;
import com.example.myapplication.Shape.IShape;
import com.example.myapplication.Shape.IShapeBuider;
import com.example.myapplication.Shape.OvalShape;
import com.example.myapplication.Shape.RectShape;
import com.example.myapplication.util.FileUtil;
import com.example.myapplication.util.PermissionUtil;
import com.example.myapplication.util.SaveGson;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int WRITE_PERMISSION_CODE = 100;
    private TextView textView;
    private EditText editText;
    CanvasManager mCanvasManger;
    private SharedPreferences sharedPreferences;
    private IShapeBuider mCurrentShapeBuider = new OvalShape.Builder();
    private String fileName = "my_file";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionUtil.requestSavePermission(this, WRITE_PERMISSION_CODE);
        final ImageView canvasImageView = findViewById(R.id.canvas);
        canvasImageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                canvasImageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mCanvasManger = new CanvasBuider().setHeight(canvasImageView.getHeight()).setWidth(canvasImageView.getWidth()).build();
                mCanvasManger.onCreate();
                mCanvasManger.getLastListShape();
                canvasImageView.setImageBitmap(mCanvasManger.getCurrantCanvas());
            }
        });
        initCanvasImageView(canvasImageView);
        findViewById(R.id.save).setOnClickListener(v -> {
            FileUtil.saveToFile(mCanvasManger.getCurrantCanvas(), "file0.png");
        });
    }

    protected void initCanvasImageView(final ImageView canasView){

        findViewById(R.id.rect).setOnClickListener((v) ->onclick(v));
        findViewById(R.id.oval).setOnClickListener((v) ->onclick(v));

        canasView.setOnTouchListener(new View.OnTouchListener() {
            PointF mStartPoint;
            PointF mEndPoint;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                PointF point = new PointF();
                point.x = event.getX();
                point.y = event.getY();
                if(event.getActionMasked() == MotionEvent.ACTION_DOWN){
                    mStartPoint = point;
                }else if(event.getActionMasked()==MotionEvent.ACTION_MOVE){
                    if(mStartPoint!=null){
                        mEndPoint = point;
                        RectF rectF = new RectF();
                        rectF.top = Math.min(mStartPoint.y,mEndPoint.y);
                        rectF.bottom = Math.max(mStartPoint.y,mEndPoint.y);
                        rectF.left = Math.min(mStartPoint.x,mEndPoint.x);
                        rectF.right = Math.max(mStartPoint.x,mEndPoint.x);
                        mCanvasManger.clearCache();
                        mCanvasManger.drawCache(mCurrentShapeBuider.buildShape(rectF,0xff000000));
                        canasView.setImageBitmap(mCanvasManger.getCurrantCanvas());
                    }
                }else if(event.getActionMasked()==MotionEvent.ACTION_UP
                || event.getActionMasked()== MotionEvent.ACTION_CANCEL){
                    if(mStartPoint!=null){
                        mEndPoint = point;
                        RectF rectF = new RectF();
                        rectF.top = Math.min(mStartPoint.y,mEndPoint.y);
                        rectF.bottom = Math.max(mStartPoint.y,mEndPoint.y);
                        rectF.left = Math.min(mStartPoint.x,mEndPoint.x);
                        rectF.right = Math.max(mStartPoint.x,mEndPoint.x);
                        mCanvasManger.clearCache();
                        mCanvasManger.drawCache(mCurrentShapeBuider.buildShape(rectF,0xff000000));
                        mCanvasManger.saveCanvas();
                        mStartPoint = null;
                        canasView.setImageBitmap(mCanvasManger.getCurrantCanvas());
                        mCanvasManger.setLastListShape();
                    }
                }
                return true;
            }
        });
    }

    protected void onDestroy(){
        super.onDestroy();
        mCanvasManger.onDestroy();
    }
    public void WriteSharedPreferences(View view){
        String content = editText.getText().toString();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("content",content);
        boolean isCommitSuccessful = editor.commit();

    }
    public void onclick(View view){
        int id = view.getId();
        if(id == R.id.rect){
            mCurrentShapeBuider=new RectShape.Builder();
        }else if(id == R.id.oval){
            mCurrentShapeBuider=new OvalShape.Builder();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == WRITE_PERMISSION_CODE) {
            if (!PermissionUtil.hasSavePermission()) {
                finish();
            }
        }
    }
}