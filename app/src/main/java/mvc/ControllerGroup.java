package mvc;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.CallSuper;

public class ControllerGroup extends BaseController {
    private List<BaseController> mController = new ArrayList<>();

    public ControllerGroup(BaseActivity activity) {
        super(activity);
    }

    public void addController(BaseController controller) {
        if (mController.contains(controller)) {
            return;
        }
        mController.add(controller);
    }

    @CallSuper
    public void onCreate(View view) {
        super.onCreate(view);
        for (BaseController baseController : mController) {
            baseController.onCreate(view);
        }
    }

    @CallSuper
    public void onStart() {
        super.onStart();
        for (BaseController baseController : mController) {
            baseController.onStart();
        }
    }

    @CallSuper
    public void onResume() {
        super.onResume();
        for (BaseController baseController : mController) {
            baseController.onResume();
        }
    }

    @CallSuper
    public void onPause() {
        super.onPause();
        for (BaseController baseController : mController) {
            baseController.onPause();
        }
    }

    @CallSuper
    public void onStop() {
        super.onStop();
        for (BaseController baseController : mController) {
            baseController.onStop();
        }
    }

    @CallSuper
    public void onDestroy() {
        super.onDestroy();
        for (BaseController baseController : mController) {
            baseController.onDestroy();
        }
    }
}
