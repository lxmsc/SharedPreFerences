package com.example.myapplication.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class PermissionUtil {

    public static boolean hasSavePermission() {
        int signal = ActivityCompat.checkSelfPermission(AppEnv.sContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readSignal = ActivityCompat.checkSelfPermission(AppEnv.sContext,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        return PackageManager.PERMISSION_GRANTED == signal
                && PackageManager.PERMISSION_GRANTED == readSignal;
    }

    public static void requestSavePermission(Activity activity, int requestCode) {
        if (hasSavePermission()) {
            return;
        }
        ActivityCompat.requestPermissions(activity,
                new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                requestCode);
    }
}
