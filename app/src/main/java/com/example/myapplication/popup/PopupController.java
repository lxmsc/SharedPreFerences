package com.example.myapplication.popup;

import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.example.myapplication.R;
import com.example.myapplication.Shape.IShapeBuider;
import com.example.myapplication.Shape.LineShape;
import com.example.myapplication.Shape.OvalShape;
import com.example.myapplication.Shape.RectShape;
import com.example.myapplication.Shape.RoundShape;
import com.example.myapplication.util.AppEnv;

import io.reactivex.ObservableEmitter;
import mvc.BaseActivity;
import mvc.BaseController;


public class PopupController extends BaseController {
    private PopupWindow mPopupWindow;
    private ObservableEmitter<PopShapeData> emitter;
    private IShapeBuider mIShapeBuilder = new OvalShape.Builder();

    public PopupController(BaseActivity activity) {
        super(activity);
    }

    public void onCreate(View view) {
        super.onCreate(view);
        view.findViewById(R.id.pop).setOnClickListener(this::initPopWindow);
        mActivity.registerDataProvider(PopShapeData.class, () -> new PopShapeData(mIShapeBuilder));
    }

    private void initPopWindow(View vie) {
        if (mPopupWindow == null) {
            View view = LayoutInflater.from(AppEnv.sContext).inflate(R.layout.item_popup, null, false);
            mPopupWindow = new PopupWindow(view,
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
            mPopupWindow.setTouchable(true);
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));    //要为popWindow设置一个背景才有效
            mPopupWindow.showAsDropDown(vie, 50, 0);
            view.findViewById(R.id.rect).setOnClickListener((v) -> onclick(v));
            view.findViewById(R.id.oval).setOnClickListener((v) -> onclick(v));
            view.findViewById(R.id.line).setOnClickListener((v) -> onclick(v));
            view.findViewById(R.id.round).setOnClickListener((v) -> onclick(v));
        } else {
            if (mPopupWindow.isShowing()) {
                mPopupWindow.dismiss();
            } else {
                mPopupWindow.showAsDropDown(vie, 50, 0);
            }
        }
    }

    public void onclick(View view) {
        int id = view.getId();
        if (id == R.id.rect) {
            mIShapeBuilder = new RectShape.Builder();
        } else if (id == R.id.oval) {
            mIShapeBuilder = new OvalShape.Builder();
        } else if (id == R.id.line) {
            mIShapeBuilder = new LineShape.Builder();
        }else if (id == R.id.round) {
            mIShapeBuilder = new RoundShape.Builder();
        }
    }
}
