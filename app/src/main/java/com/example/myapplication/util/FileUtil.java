package com.example.myapplication.util;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

import androidx.core.app.ActivityCompat;

import static android.os.Environment.getExternalStorageDirectory;

public class FileUtil {
    private static final String TAG = "FileUtil";
    private static final String MAIN_DIR = "MyFile";
    public static void saveToFile(Bitmap bitmap,String fileName) {
        if (bitmap == null) {
            return;
        }
        try {
            File file = new File(getExternalDir(), fileName);
            if (file.getParentFile().exists() || file.getParentFile().mkdirs()) {
                if (file.exists() || file.createNewFile()) {
                    FileOutputStream out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                    out.flush();
                    out.close();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "save bitmap failed", e);
        }
    }

    public static String getMainDir(){
       if (PermissionUtil.hasSavePermission()) {
           return getExternalDir();
       } else {
           return getAppDir();
       }
    }

    public static String getExternalDir() {
        return getExternalStorageDirectory() + File.separator + MAIN_DIR;
    }

    public static String getAppDir() {
        return AppEnv.sContext.getFilesDir() + File.separator + MAIN_DIR;
    }
}
