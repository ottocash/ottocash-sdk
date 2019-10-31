package com.otto.sdk.ui.activity.kycupgrade;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.Facing;
import com.otto.sdk.AppActivity;
import com.otto.sdk.R;

import app.beelabs.com.codebase.base.BaseActivity;

public class CaptureKTPActivity extends AppActivity implements View.OnClickListener {

    CameraView cameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_ktp);
      initComponent();


    }

    private void initComponent() {
        cameraView = findViewById(R.id.camera_view);

    }


    @Override
    public void onClick(View view) {

    }
}
