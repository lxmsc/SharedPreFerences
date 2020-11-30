package mvc;

import android.app.Activity;
import android.view.View;

import androidx.annotation.CallSuper;

public class BaseController {
    protected View mRootView;
    protected BaseActivity mActivity;

    public BaseController(BaseActivity activity) {
        mActivity = activity;
    }

    @CallSuper
    public void onCreate(View view) {
        mRootView = view;
    }

    @CallSuper
    public void onStart() {}

    @CallSuper
    public void onResume() {}

    @CallSuper
    public void onPause() {}

    @CallSuper
    public void onStop() {}

    @CallSuper
    public void onDestroy() {}
}
