package com.example.myapplication;

import androidx.annotation.NonNull;
import mvc.BaseActivity;
import top.defaults.colorpicker.ColorPickerPopup;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.example.myapplication.Canvas.CanvasBuider;
import com.example.myapplication.Canvas.CanvasManager;
import com.example.myapplication.popup.PopShapeData;
import com.example.myapplication.popup.PopupController;
import com.example.myapplication.util.FileUtil;
import com.example.myapplication.util.PermissionUtil;
import com.example.myapplication.util.SaveGson;

public class MainActivity extends BaseActivity {
    private static final int WRITE_PERMISSION_CODE = 100;
    CanvasManager mCanvasManger;
    private SharedPreferences sharedPreferences;
    int mColor = 0xFFFFFFFF;
    private String sColorKey = "colorkey";
    Paint mCurrentPaint = new Paint();
    private SeekBar sb_normal;
    private Context mContext = MainActivity.this;
    private PopShapeData popShapeData = new PopShapeData();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(SaveGson.get(sColorKey, int.class)==null){
            mColor = 0xFFFFFFFF;
        }
        else{
            mColor = SaveGson.get(sColorKey,int.class);
        }

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
        findViewById(R.id.color).setOnClickListener((v) ->openColor(v));
        findViewById(R.id.color).setBackgroundColor(mColor);
        initCanvasImageView(canvasImageView);
        findViewById(R.id.save).setOnClickListener(v -> {
            FileUtil.saveToFile(mCanvasManger.getCurrantCanvas(), "file0.png");
        });

        mCurrentPaint.setStyle(Paint.Style.STROKE);
        bindViews();

    }

    protected void initCanvasImageView(final ImageView canasView){

        findViewById(R.id.empty).setOnClickListener((v) ->onClear(v,canasView));

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
                        mCanvasManger.clearCache();
                        mCanvasManger.drawCache(getData(PopShapeData.class, null)
                                .iShapeBuider.buildShape(mStartPoint, mEndPoint, mCurrentPaint));
                        canasView.setImageBitmap(mCanvasManger.getCurrantCanvas());
                    }
                }else if(event.getActionMasked()==MotionEvent.ACTION_UP
                || event.getActionMasked()== MotionEvent.ACTION_CANCEL){
                    if(mStartPoint!=null){
                        mEndPoint = point;
                        mCanvasManger.clearCache();
                        mCanvasManger.drawCache(getData(PopShapeData.class, null)
                                .iShapeBuider.buildShape(mStartPoint, mEndPoint, mCurrentPaint));
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

    private void bindViews() {
        sb_normal = findViewById(R.id.sb_normal);
        sb_normal.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCurrentPaint.setStrokeWidth(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    protected void onDestroy(){
        super.onDestroy();
        mCanvasManger.onDestroy();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initControllers() {
        addController(new PopupController(this));
    }

    public void onClear(View view,final ImageView canasView){
        mCanvasManger.onClear();
        mCanvasManger.clearCache();
        mCanvasManger.setLastListShape();
        canasView.setImageBitmap(mCanvasManger.getCurrantCanvas());
    }
    public void openColor(View view) {
        new ColorPickerPopup.Builder(this)
                .initialColor(mColor) // Set initial color
                .enableAlpha(true) // Enable alpha slider or not
                .okTitle("Choose")
                .cancelTitle("Cancel")
                .showIndicator(true)
                .showValue(true)
                .build()
                .show(view, new ColorPickerPopup.ColorPickerObserver() {
                    @Override
                    public void onColorPicked(int color) {
                        view.setBackgroundColor(color);
                        mColor = color;
                        SaveGson.save(sColorKey,mColor,int.class);
                    }

                });

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