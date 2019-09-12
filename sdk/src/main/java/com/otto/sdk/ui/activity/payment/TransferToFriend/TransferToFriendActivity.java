package com.otto.sdk.ui.activity.payment.TransferToFriend;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.DialogOnAnyDeniedMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.otto.sdk.IConfig;
import com.otto.sdk.R;
import com.otto.sdk.model.general.PhoneContact;
import com.otto.sdk.ui.component.support.UiUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import app.beelabs.com.codebase.base.BaseActivity;

public class TransferToFriendActivity extends BaseActivity {

    ImageView ivBack;
    EditText etSearch;
    ImageView imgContact;
    Button btnSubmit;

    private PhoneContact mPhoneContact;
    private String numberContact;
    private String nameContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_to_friend);

        initComponent();
    }

    private void initComponent() {
        ivBack = findViewById(R.id.ivBack);
        etSearch = findViewById(R.id.et_search);
        imgContact = findViewById(R.id.img_contact);
        btnSubmit = findViewById(R.id.btn_submit);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        imgContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPermission();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String phone = etSearch.getText().toString();
                if (phone.isEmpty()) {
                    Toast.makeText(TransferToFriendActivity.this, "Masukkan nomor handphone", Toast.LENGTH_SHORT).show();
                } else {
                    transferToFriend();
                }
            }
        });

    }

    private void initPermission() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_CONTACTS)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        pickContact();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied())
                            showSettingsDialog();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void pickContact() {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
        startActivityForResult(pickContactIntent, 200);
    }

    private void showSettingsDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 101);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == 200) {
                Uri contactUri = data.getData();
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER,
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};

                if (TransferToFriendActivity.this.getContentResolver() == null || contactUri == null) {
                    return;
                }

                Cursor cursor = TransferToFriendActivity.this.getContentResolver()
                        .query(contactUri, projection, null, null, null);

                if (cursor != null) {
                    cursor.moveToFirst();

                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    PhoneContact phoneContact = new PhoneContact();
                    phoneContact.setId("");
                    phoneContact.setName(name);
                    phoneContact.setMobileNumber(number);

                    addReceiver(phoneContact);
                    cursor.close();
                }
            }
        }
    }


    private void addReceiver(PhoneContact phoneContact) {
        etSearch.setText(UiUtil.countryCodeReplacedWithZero(phoneContact.getMobileNumber()));
        etSearch.setSelection(etSearch.getText().toString().length());

        mPhoneContact = phoneContact;
    }


    public void transferToFriend() {

        if (mPhoneContact != null) {
            mPhoneContact.setMobileNumber(UiUtil.countryCodeReplacedWithZero(mPhoneContact.getMobileNumber()));
            numberContact = mPhoneContact.getMobileNumber();
            nameContact = mPhoneContact.getName();
        } else {
            numberContact = etSearch.getText().toString();
            nameContact = "-";
        }

        Intent intent = new Intent(TransferToFriendActivity.this, TransferToFriendSendActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(IConfig.KEY_NUMBER_CONTACT, numberContact);
        intent.putExtra(IConfig.KEY_NAME_CONTACT, nameContact);
        startActivity(intent);
    }

}
