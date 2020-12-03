package com.example.myapplication;

import androidx.annotation.ColorInt;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.example.myapplication.Canvas.CanvasBuider;
import com.example.myapplication.Canvas.CanvasManager;
import com.example.myapplication.Shape.IShape;
import com.example.myapplication.popup.PopShapeData;
import com.example.myapplication.popup.PopupController;
import com.example.myapplication.util.FileUtil;
import com.example.myapplication.util.PermissionUtil;
import com.example.myapplication.util.SaveGson;

import static com.example.myapplication.Shape.IShape.getRawShape;

public class MainActivity extends BaseActivity {
    private static final int WRITE_PERMISSION_CODE = 100;
    CanvasManager mCanvasManger;
    private SharedPreferences sharedPreferences;
    @ColorInt
    int mColor = 0xFFFFFFFF;
    float mPaintWidth = 10;
    private String sColorKey = "colorkey";
    Paint mCurrentPaint = new Paint();
    private SeekBar sb_normal;
    private PopShapeData mPopShapeData = new PopShapeData();
    Button button_cursor;
    int mCursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(SaveGson.get(sColorKey, int.class)==null){
            mColor = 0xFFFFFFFF;
            mCurrentPaint.setColor(mColor);
        }
        else{
            mColor = SaveGson.get(sColorKey,int.class);
            mCurrentPaint.setColor(mColor);
        }

        mCurrentPaint.setStyle(Paint.Style.STROKE);
        mCurrentPaint.setStrokeWidth(10);
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
        button_cursor = (Button) findViewById(R.id.cursor);
        button_cursor.setText(R.string.cursor_draw);
        mCursor=0;
        button_cursor.setOnClickListener(v->{
            mCursor ^= 1;
            if(mCursor==0){
                button_cursor.setText(R.string.cursor_draw);
            }
            else{
                button_cursor.setText(R.string.cursor_chose);
            }

        });
        bindViews();

    }
    protected void initCanvasImageView(final ImageView canasView) {
        findViewById(R.id.empty).setOnClickListener(v -> onClear(v, canasView));

        canasView.setOnTouchListener(new View.OnTouchListener() {
            PointF mStartPoint;
            PointF mEndPoint;
            IShape iShape;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int shapeType = getData(PopShapeData.class, mPopShapeData).iShapeBuider.buildShape().getType();
                PointF pointF = new PointF();
                pointF.x = event.getX();
                pointF.y = event.getY();

                if(mCursor == 1){
                    if(event.getActionMasked() == MotionEvent.ACTION_DOWN){
                        mCanvasManger.clearCache();
                        iShape = mCanvasManger.getChoseShape(pointF);
                        if(iShape!=null) {
                            mStartPoint = pointF;
                            mCanvasManger.drawCache(iShape);
                            canasView.setImageBitmap(mCanvasManger.getCurrantCanvas());
                        }
                    }
                    else if(event.getActionMasked() == MotionEvent.ACTION_MOVE){
                        if(iShape!=null&&mStartPoint!=null) {
                            mEndPoint = pointF;
                            mCanvasManger.clearCache();
                            PointF pointF1 = new PointF(mEndPoint.x - mStartPoint.x, mEndPoint.y - mStartPoint.y);
                            mCanvasManger.drawCache(iShape.addPoint(pointF1));
                            canasView.setImageBitmap(mCanvasManger.getCurrantCanvas());
                        }
                    }
                    else if(event.getActionMasked() == MotionEvent.ACTION_UP
                            || event.getActionMasked() == MotionEvent.ACTION_CANCEL){
                        if(iShape!=null&&mStartPoint!=null) {
                            mCanvasManger.clearCache();
                            mEndPoint = pointF;
                            PointF pointF1 = new PointF(mEndPoint.x - mStartPoint.x, mEndPoint.y - mStartPoint.y);
                            mCanvasManger.drawCache(iShape.addPoint(pointF1));
                            mCanvasManger.saveCanvas();
                            mStartPoint = null;
                            canasView.setImageBitmap(mCanvasManger.getCurrantCanvas());
                            mCanvasManger.setLastListShape();
                        }
                    }
                }
                else if(mCursor==2){
                    mCanvasManger.getChoseShape(pointF);
                    canasView.setImageBitmap(mCanvasManger.getCurrantCanvas());

                }
                else if(mCursor==0) {
                    if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                        if (shapeType == 3) {
                            mStartPoint = pointF;
                        } else {
                            mStartPoint = new PointF(pointF.x - 200, pointF.y - 100);
                            mEndPoint = new PointF(pointF.x + 200, pointF.y + 100);
                            mCanvasManger.clearCache();
                            mCanvasManger.drawCache(getData(PopShapeData.class, mPopShapeData)
                                    .iShapeBuider.buildShape(mStartPoint, mEndPoint, mColor, mPaintWidth));
                            canasView.setImageBitmap(mCanvasManger.getCurrantCanvas());
                        }

                    } else if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
                        if (shapeType == 3) {
                            if (mStartPoint != null) {
                                mEndPoint = pointF;
                                mCanvasManger.clearCache();
                                mCanvasManger.drawCache(getData(PopShapeData.class, mPopShapeData)
                                        .iShapeBuider.buildShape(mStartPoint, mEndPoint, mColor, mPaintWidth));
                                canasView.setImageBitmap(mCanvasManger.getCurrantCanvas());
                            }
                        } else {
                            mStartPoint = new PointF(pointF.x - 200, pointF.y - 100);
                            mEndPoint = new PointF(pointF.x + 200, pointF.y + 100);
                            mCanvasManger.clearCache();
                            mCanvasManger.drawCache(getData(PopShapeData.class, mPopShapeData)
                                    .iShapeBuider.buildShape(mStartPoint, mEndPoint, mColor, mPaintWidth));
                            canasView.setImageBitmap(mCanvasManger.getCurrantCanvas());
                        }
                    } else if (event.getActionMasked() == MotionEvent.ACTION_UP
                            || event.getActionMasked() == MotionEvent.ACTION_CANCEL) {
                        if (shapeType == 3) {
                            if (mStartPoint != null) {
                                mEndPoint = pointF;
                                mCanvasManger.clearCache();
                                mCanvasManger.drawCache(getData(PopShapeData.class, mPopShapeData)
                                        .iShapeBuider.buildShape(mStartPoint, mEndPoint, mColor, mPaintWidth));
                                mCanvasManger.saveCanvas();
                                mStartPoint = null;
                                canasView.setImageBitmap(mCanvasManger.getCurrantCanvas());
                                mCanvasManger.setLastListShape();
                            }
                        } else {
                            mStartPoint = new PointF(pointF.x - 200, pointF.y - 100);
                            mEndPoint = new PointF(pointF.x + 200, pointF.y + 100);
                            mCanvasManger.clearCache();
                            mCanvasManger.drawCache(getData(PopShapeData.class, mPopShapeData)
                                    .iShapeBuider.buildShape(mStartPoint, mEndPoint, mColor, mPaintWidth));
                            mCanvasManger.saveCanvas();
                            mStartPoint = null;
                            canasView.setImageBitmap(mCanvasManger.getCurrantCanvas());
                            mCanvasManger.setLastListShape();
                        }
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
                mPaintWidth=progress;
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