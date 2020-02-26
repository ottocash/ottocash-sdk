package com.otto.sdk.ui.component.support;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.annotation.Nullable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class Base64ImageUtil {

    private static int qualityImage = 25;
    private static final String prefixBase64 = "data:image/jpg;base64,";

    @Nullable
    public static String createImageBase64(File file) {
        if (file == null) return null;
        if (!file.exists()) return null;

        /*long fileSize = file.length() / 1024;
        if (fileSize >= 2500) {
            qualityImage = 55;
        } else if (fileSize >= 1500) {
            qualityImage = 65;
        }*/

        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, qualityImage, byteArrayOutputStream);

        return prefixBase64 + Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
    }
}
