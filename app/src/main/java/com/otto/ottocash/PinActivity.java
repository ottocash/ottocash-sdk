package com.otto.ottocash;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.otto.ottocash.livedata.PinLiveData;
import com.otto.sdk.ui.view.PinCodeView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PinActivity extends AppCompatActivity implements PinAdapter.Callback {


    @BindView(R.id.pinCodeTitle)
    TextView pinCodeTitle;
    @BindView(R.id.desc)
    TextView desc;
    @BindView(R.id.textViewForgotPin)
    TextView textViewForgotPin;
    @BindView(R.id.pinList)
    RecyclerView pinList;
    PinLiveData<String> pin = new PinLiveData<>();
    @BindView(R.id.relativeLayout)
    PinCodeView pinCodeView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        ButterKnife.bind(this);

        pinList.setLayoutManager(new GridLayoutManager(this, 3));
        pinList.setAdapter(new PinAdapter(this));
    }

    @Override
    public void onUpdatePin(String digit) {
        pinCodeView.input(digit);
        Toast.makeText(PinActivity.this, pinCodeView.getCode(), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onDeletePin() {
        pinCodeView.delete();
    }
}
