package com.otto.sdk;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import android.util.Log;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.otto.sdk.custom.CustomProgressDialog;
import com.otto.sdk.model.general.MyLastLocation;
import com.otto.sdk.ui.component.support.Logging;
import com.otto.sdk.ui.component.support.Preferences;

import java.util.List;

import app.beelabs.com.codebase.base.BaseActivity;

public class AppActivity extends BaseActivity {

    private final String TAG = this.getClass().getSimpleName();

    private final int REQUEST_ACCESS_FINE_LOCATION = 1080;

    protected MyLastLocation myLastLocation = new MyLastLocation();

    protected Preferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = new Preferences(this);

    }

    protected void getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // request write permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_ACCESS_FINE_LOCATION);
            return;
        }

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }

        try {
            myLastLocation.setLatitude(bestLocation.getLatitude());
            myLastLocation.setLongitude(bestLocation.getLongitude());
        } catch (Exception e) {
            e.printStackTrace();
            Logging.debug(TAG, e.getMessage());

            myLastLocation.setLatitude(0.0);
            myLastLocation.setLongitude(0.0);

        } finally {
            if (bestLocation != null) {
                locationManager.requestLocationUpdates(bestLocation.getProvider(), 2000, 10, locationListener);
            }
        }
    }


    private final LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                Log.d(TAG, " Location Lat : " + location.getLatitude());
                Log.d(TAG, " Location Lng : " + location.getLongitude());
                myLastLocation.setLatitude(location.getLatitude());
                myLastLocation.setLongitude(location.getLongitude());
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == REQUEST_ACCESS_FINE_LOCATION) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted
                getLastKnownLocation();
            }
        }
    }

    public MyLastLocation getMyLastLocation() {
        return myLastLocation;
    }


    /**
     * LOADING PROGRESS
     */
    public void showProgress(Context context) {
        CustomProgressDialog.showDialog(context);
    }

    public void showProgress(Context context, String loadingText) {
        CustomProgressDialog.showDialog(context, loadingText);
    }

    public void closeProgress() {
        CustomProgressDialog.closeDialog();
    }

    /**
     * PERMISSION
     */
    public void initPermissionMultiple(MultiplePermissionsListener listener){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();
    }



    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    public void showSettingsDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            dialog.cancel();

            openSettings();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }
}

