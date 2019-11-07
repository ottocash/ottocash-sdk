package com.otto.sdk.ui.activity.kycupgrade;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import com.otto.sdk.IConfig;
import com.otto.sdk.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import app.beelabs.com.codebase.support.util.CacheUtil;

public class CameraUtil {

    public static Bitmap getPhotoCameraResult(Intent data, String preferenceKey, Context context) {
        Bitmap thumbnail = null;

        Uri thumbnailUri;
        try {
            thumbnailUri = (Uri) data.getExtras().get(MediaStore.EXTRA_OUTPUT);
        } catch (Exception e) {
            Log.e("ImageUtil:", e.getMessage());
            return null;
        }

        try {
            thumbnail = MediaStore.Images.Media.getBitmap(context.getContentResolver(), thumbnailUri);
            if (preferenceKey.equals(IConfig.KEY_BASE64_SELFIE_CAMERA_KTP)) {
                Matrix mtx = new Matrix();

                Bitmap bitmap = resizeImage(Bitmap.createBitmap(thumbnail, 0, 0, thumbnail.getWidth(), thumbnail.getHeight(), mtx, true));
                CacheUtil.putPreferenceString(preferenceKey, convertImageToBase64(bitmap), context);
                thumbnail = bitmap;
            } else {
                CacheUtil.putPreferenceString(preferenceKey, convertImageToBase64(resizeImage(thumbnail)), context);
            }


            // remove photo file after capture camera
            if (IConfig.REMOVE_CAMERA_PHOTO_TAKEN_CONFIG)
                FileUtil.removeDirectory(new File(Environment.getExternalStorageDirectory() + "/" + context.getResources().getString(R.string.app_name)));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return thumbnail;
    }

    public static void saveBitmap(Bitmap bm, String preferenceKey, Context context) {
        CacheUtil.putPreferenceString(preferenceKey, convertImageToBase64(resizeImage(bm)), context);
    }

    public static String convertImageToBase64(Bitmap bitmap) {
        try {
            String header = "data:image/jpeg;base64,";
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
            byte[] image = stream.toByteArray();

            return header + Base64.encodeToString(image, 0).replace("\n", "");
        } catch (Exception e) {
            return null;
        }
    }

    private static Bitmap resizeImage(Bitmap bm) {
        int fixWidth = 800;
        int finalHeight = bm.getHeight();
        if (finalHeight > fixWidth) {
            //scale height to maintain aspect ratio
            finalHeight = (fixWidth * bm.getHeight()) / bm.getWidth();
        }

        return Bitmap.createScaledBitmap(bm, fixWidth, finalHeight, true);
    }
}
