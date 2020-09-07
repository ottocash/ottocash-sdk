package com.otto.sdk.ui.activity.topup;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.otto.sdk.IConfig;
import com.otto.sdk.R;
import com.otto.sdk.model.general.TopUpGuide;
import com.otto.sdk.ui.adapter.TopUpGuideAdapter;

import java.util.ArrayList;
import java.util.List;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.support.util.CacheUtil;

public class TopUpActivity extends BaseActivity {

    String mobilePhoneNumber;
    ImageView imgBack;
    RecyclerView recyclerView;

    private List<TopUpGuide> mTopUpGuideList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);

        initComponentUi();
        initRecyclerView();
        displayTopUpGuide();

    }

    private void initComponentUi() {
        imgBack = findViewById(R.id.ivBack);
        recyclerView = findViewById(R.id.recycler_view);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);

        final LinearLayoutManager menuListLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(menuListLayoutManager);
    }

    private void displayTopUpGuide() {
        List<TopUpGuide> topUpGuideList = getTopUpGuideData();

        TopUpGuideAdapter adapter = new TopUpGuideAdapter(this, topUpGuideList);
        recyclerView.setAdapter(adapter);
    }

    private List<TopUpGuide> getTopUpGuideData() {

         mobilePhoneNumber = CacheUtil.getPreferenceString(IConfig.OC_SESSION_PHONE, TopUpActivity.this);




        /**
         * Start Bank
         */
        TopUpGuide transferBank = new TopUpGuide();
        transferBank.setName(getString(R.string.top_up_with_transfer_to_bank_title));
        transferBank.setDesc(getString(R.string.top_up_with_transfer_to_bank_desc));
        transferBank.setLogo(R.drawable.ic_transfer_bank);
        transferBank.setSelected(false);

        List<TopUpGuide.Tool> transferBankTools = new ArrayList();

        /**
         * ATM
         */
        List<String> viaATMStep = new ArrayList();
        viaATMStep.add(getString(R.string.top_up_via_atm_1));
        viaATMStep.add(getString(R.string.top_up_via_atm_2));
        viaATMStep.add(getString(R.string.top_up_via_atm_3));
        viaATMStep.add(getString(R.string.top_up_via_atm_4));
        viaATMStep.add(getString(R.string.top_up_via_atm_5, mobilePhoneNumber));
        viaATMStep.add(getString(R.string.top_up_via_atm_6));
        viaATMStep.add(getString(R.string.top_up_via_atm_7));

        TopUpGuide.Tool atm = new TopUpGuide.Tool();
        atm.setPosition(0);
        atm.setName(getString(R.string.top_up_via_atm_title));
        atm.setNote("");
        atm.setSelected(false);
        atm.setStepList(viaATMStep);
        transferBankTools.add(atm);

        transferBank.setToolList(transferBankTools);
        mTopUpGuideList.add(transferBank);


        /**
         * Start Mandiri
         */
        TopUpGuide transferBankMandiri = new TopUpGuide();
        transferBankMandiri.setName(getString(R.string.top_up_with_transfer_to_mandiri_title));
        transferBankMandiri.setDesc(getString(R.string.top_up_with_transfer_to_mandiri_desc));
        transferBankMandiri.setLogo(R.drawable.ic_bank_mandiri);
        transferBankMandiri.setSelected(false);

        List<TopUpGuide.Tool> mandiriTools = new ArrayList();
        /**
         * Atm
         */
        List<String> viaAtmStepsMandiri = new ArrayList();
        viaAtmStepsMandiri.add(getString(R.string.top_up_via_atm_mandiri_1));
        viaAtmStepsMandiri.add(getString(R.string.top_up_via_atm_mandiri_2));
        viaAtmStepsMandiri.add(getString(R.string.top_up_via_atm_mandiri_3));
        viaAtmStepsMandiri.add(getString(R.string.top_up_via_atm_mandiri_4));
        viaAtmStepsMandiri.add(getString(R.string.top_up_via_atm_mandiri_5, mobilePhoneNumber));
        viaAtmStepsMandiri.add(getString(R.string.top_up_via_atm_mandiri_6));
        viaAtmStepsMandiri.add(getString(R.string.top_up_via_atm_mandiri_7));
        viaAtmStepsMandiri.add(getString(R.string.top_up_via_atm_mandiri_8));
        viaAtmStepsMandiri.add(getString(R.string.top_up_via_atm_mandiri_9));

        TopUpGuide.Tool atmMandiri = new TopUpGuide.Tool();
        atmMandiri.setPosition(0);
        atmMandiri.setName(getString(R.string.top_up_via_atm_mandiri_title));
        atmMandiri.setNote(getString(R.string.note_bank_mandiri));
        atmMandiri.setSelected(false);
        atmMandiri.setStepList(viaAtmStepsMandiri);
        mandiriTools.add(atmMandiri);

        /**
         * Mobile
         */
        List<String> viaMobileStepsMandiri = new ArrayList();
        viaMobileStepsMandiri.add(getString(R.string.top_up_via_mobile_mandiri_1));
        viaMobileStepsMandiri.add(getString(R.string.top_up_via_mobile_mandiri_2));
        viaMobileStepsMandiri.add(getString(R.string.top_up_via_mobile_mandiri_3));
        viaMobileStepsMandiri.add(getString(R.string.top_up_via_mobile_mandiri_4));
        viaMobileStepsMandiri.add(getString(R.string.top_up_via_mobile_mandiri_5, mobilePhoneNumber));
        viaMobileStepsMandiri.add(getString(R.string.top_up_via_mobile_mandiri_6));
        viaMobileStepsMandiri.add(getString(R.string.top_up_via_mobile_mandiri_7));
        viaMobileStepsMandiri.add(getString(R.string.top_up_via_mobile_mandiri_8));

        TopUpGuide.Tool mobileMandiri = new TopUpGuide.Tool();
        mobileMandiri.setPosition(1);
        mobileMandiri.setName(getString(R.string.top_up_via_mobile_mandiri_title));
        mobileMandiri.setNote(getString(R.string.note_bank_mandiri));
        mobileMandiri.setSelected(false);
        mobileMandiri.setStepList(viaMobileStepsMandiri);
        mandiriTools.add(mobileMandiri);

        /**
         * Internet
         */
        List<String> internetStepsMandiri = new ArrayList();
        internetStepsMandiri.add(getString(R.string.top_up_via_internet_mandiri_1));
        internetStepsMandiri.add(getString(R.string.top_up_via_internet_mandiri_2));
        internetStepsMandiri.add(getString(R.string.top_up_via_internet_mandiri_3));
        internetStepsMandiri.add(getString(R.string.top_up_via_internet_mandiri_4));
        internetStepsMandiri.add(getString(R.string.top_up_via_internet_mandiri_5, mobilePhoneNumber));
        internetStepsMandiri.add(getString(R.string.top_up_via_internet_mandiri_6));
        internetStepsMandiri.add(getString(R.string.top_up_via_internet_mandiri_7));
        internetStepsMandiri.add(getString(R.string.top_up_via_internet_mandiri_8));

        TopUpGuide.Tool internetMandiri = new TopUpGuide.Tool();
        internetMandiri.setPosition(2);
        internetMandiri.setName(getString(R.string.top_up_via_internet_mandiri_title));
        internetMandiri.setNote(getString(R.string.note_bank_mandiri));
        internetMandiri.setSelected(false);
        internetMandiri.setStepList(internetStepsMandiri);
        mandiriTools.add(internetMandiri);

        transferBankMandiri.setToolList(mandiriTools);
        mTopUpGuideList.add(transferBankMandiri);


        /**
         * Start BCA
         */
        TopUpGuide transferBankBca = new TopUpGuide();
        transferBankBca.setName(getString(R.string.top_up_with_transfer_to_bca_title));
        transferBankBca.setDesc(getString(R.string.top_up_with_transfer_to_bca_desc));
        transferBankBca.setLogo(R.drawable.ic_bank_bca);
        transferBankBca.setSelected(false);

        List<TopUpGuide.Tool> bcaTools = new ArrayList();

        /**
         * Mobile
         */
        List<String> viaMobileStepsBca = new ArrayList();
        viaMobileStepsBca.add(getString(R.string.top_up_via_mobile_bca_1));
        viaMobileStepsBca.add(getString(R.string.top_up_via_mobile_bca_2));
        viaMobileStepsBca.add(getString(R.string.top_up_via_mobile_bca_3));
        viaMobileStepsBca.add(getString(R.string.top_up_via_mobile_bca_4, mobilePhoneNumber));
        viaMobileStepsBca.add(getString(R.string.top_up_via_mobile_bca_5));
        viaMobileStepsBca.add(getString(R.string.top_up_via_mobile_bca_6));
        viaMobileStepsBca.add(getString(R.string.top_up_via_mobile_bca_7));
        viaMobileStepsBca.add(getString(R.string.top_up_via_mobile_bca_8));

        TopUpGuide.Tool mobileBca = new TopUpGuide.Tool();
        mobileBca.setPosition(0);
        mobileBca.setName(getString(R.string.top_up_via_mobile_bca_title));
        mobileBca.setNote("");
        mobileBca.setSelected(false);
        mobileBca.setStepList(viaMobileStepsBca);
        bcaTools.add(mobileBca);

        /**
         * Atm
         */
        List<String> viaAtmStepsBca = new ArrayList();
        viaAtmStepsBca.add(getString(R.string.top_up_via_atm_bca_1));
        viaAtmStepsBca.add(getString(R.string.top_up_via_atm_bca_2));
        viaAtmStepsBca.add(getString(R.string.top_up_via_atm_bca_3));
        viaAtmStepsBca.add(getString(R.string.top_up_via_atm_bca_4));
        viaAtmStepsBca.add(getString(R.string.top_up_via_atm_bca_5, mobilePhoneNumber));
        viaAtmStepsBca.add(getString(R.string.top_up_via_atm_bca_6));
        viaAtmStepsBca.add(getString(R.string.top_up_via_atm_bca_7));
        viaAtmStepsBca.add(getString(R.string.top_up_via_atm_bca_8));

        TopUpGuide.Tool atmBca = new TopUpGuide.Tool();
        atmBca.setPosition(1);
        atmBca.setName(getString(R.string.top_up_via_atm_bca_title));
        atmBca.setNote("");
        atmBca.setSelected(false);
        atmBca.setStepList(viaAtmStepsBca);
        bcaTools.add(atmBca);

        /**
         * Klik
         */
        List<String> viaKlikStepsBca = new ArrayList();
        viaKlikStepsBca.add(getString(R.string.top_up_via_klik_bca_1));
        viaKlikStepsBca.add(getString(R.string.top_up_via_klik_bca_2));
        viaKlikStepsBca.add(getString(R.string.top_up_via_klik_bca_3));
        viaKlikStepsBca.add(getString(R.string.top_up_via_klik_bca_4, mobilePhoneNumber));
        viaKlikStepsBca.add(getString(R.string.top_up_via_klik_bca_5));
        viaKlikStepsBca.add(getString(R.string.top_up_via_klik_bca_6));
        viaKlikStepsBca.add(getString(R.string.top_up_via_klik_bca_7));

        TopUpGuide.Tool klikBca = new TopUpGuide.Tool();
        klikBca.setPosition(2);
        klikBca.setName(getString(R.string.top_up_via_klik_bca_title));
        klikBca.setNote("");
        klikBca.setSelected(false);
        klikBca.setStepList(viaKlikStepsBca);
        bcaTools.add(klikBca);

        transferBankBca.setToolList(bcaTools);
        mTopUpGuideList.add(transferBankBca);




        return mTopUpGuideList;
    }

    public void parentSelected(int position) {
        int pos = 0;
        for (TopUpGuide topUpGuide : mTopUpGuideList) {
            if (pos == position) {
                mTopUpGuideList.get(pos).setSelected(!topUpGuide.isSelected());
            } else {
                mTopUpGuideList.get(pos).setSelected(false);
            }
            pos++;
        }

        refreshAdapter();
    }

    public void childSelected(int position, int childPosition) {
        int pos = 0;
        for (TopUpGuide.Tool tool : mTopUpGuideList.get(position).getToolList()) {
            if (pos == childPosition) {
                mTopUpGuideList.get(position).getToolList().get(pos).setSelected(!tool.isSelected());
            } else {
                mTopUpGuideList.get(position).getToolList().get(pos).setSelected(false);
            }
            pos++;
        }

        refreshAdapter();
    }

    private void refreshAdapter() {
        TopUpGuideAdapter adapter = (TopUpGuideAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
