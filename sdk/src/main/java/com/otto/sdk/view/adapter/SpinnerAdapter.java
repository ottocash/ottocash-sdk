package com.otto.sdk.view.adapter;

import android.content.Context;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.otto.sdk.R;
import com.otto.sdk.model.api.misc.SecurityQuestionsItem;


import java.util.ArrayList;
import java.util.List;

public class SpinnerAdapter extends BaseAdapter implements android.widget.SpinnerAdapter {
    List<SecurityQuestionsItem> models = new ArrayList<>();
    Context mContext;
    int spinnerLayout, dropdownLayout;
    boolean isVirgin = true;
    boolean isCallbackReady = false;
    int currpos = -1;
    TextView hook;

    public SpinnerAdapter(Context mContext, int spinnerLayout, int dropdownLayout){
            this.mContext = mContext;
            this.spinnerLayout = spinnerLayout;
            this.dropdownLayout = dropdownLayout;
    }

    public void addModels(List<SecurityQuestionsItem> models){
            this.models = models;
            this.notifyDataSetChanged();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        SecurityQuestionsItem model = models.get(position);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(dropdownLayout, null);
        TextView tv = convertView.findViewById(R.id.tv_content);
        tv.setText(model.getQuestion());
        isVirgin = false;
        isCallbackReady = true;
        return convertView;
    }


    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int position) {
        return models.get(position);
    }

    @Override
    public long getItemId(int position) {
        return models.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    SecurityQuestionsItem model;
        try {
            model = models.get(position);
        } catch (Exception e) {
            model = models.get(0);
        }
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(spinnerLayout, null);
        TextView tv = convertView.findViewById(R.id.tv_content);
        tv.setText("Klik disini...");
        hook = tv;
        if (isVirgin == false)
            tv.setText(model.getQuestion());
        currpos = position;
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
    public boolean isCallbackReady() {
        return isCallbackReady;
    }
    public void resetAdapter() {
        models = new ArrayList<>();
        this.notifyDataSetChanged();
        isVirgin = true;
    }

    public void deVirgin(){
        isVirgin = false;
        isCallbackReady = true;
    }

    public void changeFirstItemToText() {
        if (currpos == 0) {
            hook.setText(models.get(0).getQuestion());
        }
    }


}
