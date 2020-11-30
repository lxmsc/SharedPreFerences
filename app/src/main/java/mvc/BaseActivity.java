package mvc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    ControllerGroup mControllers = new ControllerGroup(this);
    View mRootView;
    Map<Class, List<IProvider>> mDataProvider = new ConcurrentHashMap<>();

    public <T extends IData> void registerDataProvider(Class<T> clazz,
                                                       @NonNull IProvider<T> provider) {
        List<IProvider> providers = mDataProvider.get(clazz);
        if (providers == null) {
            providers = new ArrayList<IProvider>();
            mDataProvider.put(clazz, providers);
        }
        providers.add(provider);
    }

    public <T extends IData> T getData(Class<T> clazz, @Nullable T defalutData) {
        List<IProvider> providers = mDataProvider.get(clazz);
        if (providers == null || providers.size() == 0) {
            return defalutData;
        }
        List<T> datas = new ArrayList<>(1);
        for (IProvider provider : providers) {
            IData data = provider.getData();
            if (data != null) {
                datas.add(clazz.cast(data));
            }
        }
        if (datas.isEmpty()) {
            return defalutData;
        }
        return datas.get(0);
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = LayoutInflater.from(this).inflate(getContentLayoutId(), null, false);
        setContentView(mRootView);
        mDataProvider.clear();
        initControllers();
        mControllers.onCreate(mRootView);
    }

    protected void onStart() {
        super.onStart();
        mControllers.onStart();
    }

    protected void onResume() {
        super.onResume();
        mControllers.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mControllers.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mControllers.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mControllers.onDestroy();
    }

    protected abstract int getContentLayoutId();

    protected abstract void initControllers();

    protected void addController(BaseController baseController) {
        mControllers.addController(baseController);
    }
}
