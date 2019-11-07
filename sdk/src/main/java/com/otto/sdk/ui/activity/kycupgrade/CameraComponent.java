package com.otto.sdk.ui.activity.kycupgrade;

import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("deprecation")
public class CameraComponent extends SurfaceView implements Camera.PictureCallback, SurfaceHolder.Callback {

    private static final String TAG = "Camera Preview";
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private Camera.Parameters mParameters;
    private boolean isCameraSelfie;
    private boolean hasFrontCamera;

    public CameraComponent(Context context, Camera camera, boolean hasFrontCamera) {
        super(context);
        mCamera = camera;
        this.isCameraSelfie = isCameraSelfie;
        this.hasFrontCamera = hasFrontCamera;

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            setParameters(mCamera, 0);
            mCamera.setPreviewDisplay(holder);
            mCamera.setDisplayOrientation(90);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
        mCamera.release();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mHolder.getSurface() == null) {
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {

            setParameters(mCamera, 0);
            mCamera.setPreviewDisplay(mHolder);
            mCamera.setDisplayOrientation(90);
            mCamera.startPreview();


        } catch (Exception e) {
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int index, double aspectRatio) {
        int w = sizes.get(index).width;
        int h = sizes.get(index).height;

        final double ASPECT_TOLERANCE = aspectRatio;

        if (sizes == null) return null;

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        int ww = 0;
        int hh = 0;
        int i = 0;
        for (Camera.Size size : sizes) {
            if (i < index) continue; // force to iterate again base on index value

            if ((size.width > ww) && (size.height > hh)) {
                ww = size.width;
                hh = size.height;
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }

            i++;
        }


        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            i = 0;
            for (Camera.Size size : sizes) {
                if (i < index) continue;
                if ((size.width > ww) && (size.height > hh)) {
                    ww = size.width;
                    hh = size.height;
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
                i++;
            }
        }

        return optimalSize;
    }

    /**
     * SetParameters - orientation portrait
     */
    public void setParameters(Camera mCamera, int index) {

        List<Camera.Size> sizes = null;


        mParameters = mCamera.getParameters();
        mParameters.set("camera-id", 2);
        mParameters.set("orientation", "portrait");
        sizes = mParameters.getSupportedPreviewSizes();


        double aspectRatio = (isCameraSelfie && hasFrontCamera) ? 0.8 : 0.4;
        Camera.Size cs = getOptimalPreviewSize(sizes, index, aspectRatio);////sizes.get(0);


        List<Integer> supportedPreviewFormats = mParameters.getSupportedPreviewFormats();
        Iterator<Integer> supportedPreviewFormatsIterator = supportedPreviewFormats.iterator();
        while (supportedPreviewFormatsIterator.hasNext()) {
            Integer previewFormat = supportedPreviewFormatsIterator.next();
            if (previewFormat == ImageFormat.YV12) {
                mParameters.setPreviewFormat(previewFormat);
            }
        }

        int w = cs.width;
        int h = cs.height;
        mParameters.setPreviewSize(w, h);
        mParameters.setPictureSize(w, h);


        List<?> focus = mParameters.getSupportedFocusModes();
        if (focus != null
                && focus.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }

        List<?> whiteMode = mParameters.getSupportedWhiteBalance();
        if (whiteMode != null
                && whiteMode
                .contains(Camera.Parameters.WHITE_BALANCE_AUTO)) {
            mParameters.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_AUTO);
        }

        List<String> flashModes = mParameters.getSupportedFlashModes();

        if (flashModes != null
                && flashModes
                .contains(Camera.Parameters.FLASH_MODE_AUTO)) {
            mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
        }


        try {
            mCamera.setParameters(mParameters); // apply the changes
        } catch (Exception e) {
            String msg = e.getMessage();
            Log.d("ERROR:", msg);
            setParameters(mCamera, index + 1);
        }

    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {

    }

}
