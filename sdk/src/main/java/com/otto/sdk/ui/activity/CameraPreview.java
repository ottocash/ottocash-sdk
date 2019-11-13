package com.otto.sdk.ui.activity;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private Camera.Parameters mParameters;
    private boolean isCameraSelfie;
    private boolean hasFrontCamera;


    public CameraPreview(Context context, Camera camera, boolean hasFrontCamera) {
        super(context);
        mCamera = camera;
        mHolder = getHolder();
        mHolder.addCallback(this);
        this.isCameraSelfie = isCameraSelfie;
        this.hasFrontCamera = hasFrontCamera;

        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }


    public void surfaceCreated(SurfaceHolder holder) {
        try {
            // create the surface and start camera preview
            if (mCamera == null) {
                setParameters(mCamera, 0);
                mCamera.setPreviewDisplay(holder);
                mCamera.setDisplayOrientation(90);
                mCamera.startPreview();
            }
        } catch (IOException e) {
            Log.d(VIEW_LOG_TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    public void refreshCamera(Camera camera) {
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
        setCamera(camera);
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (Exception e) {
            Log.d(VIEW_LOG_TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        if (mHolder.getSurface() == null) {
            // preview surface does not exist
            return;
        }

        try {
            mCamera.stopPreview();
        } catch (Exception e) {

        }

        // start preview with new settings
        try {

            setParameters(mCamera, 0);
            mCamera.setPreviewDisplay(mHolder);
            mCamera.setDisplayOrientation(90);
            mCamera.startPreview();


        } catch (Exception e) {
            Log.d("jejn", "Error starting camera preview: " + e.getMessage());
        }

        refreshCamera(mCamera);
    }

    public void setCamera(Camera camera) {
        //method to set a camera instance
        mCamera = camera;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        // mCamera.release();

    }

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


}