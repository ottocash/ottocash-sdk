package com.otto.sdk.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.otto.sdk.R;
import com.otto.sdk.model.general.MainMenuModel;

import java.util.List;

public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuAdapter.ViewHolder>  {

    private Context mContext;
    public List<MainMenuModel> mDataset;
    private int mlayout;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        // each data item is just a string in this case
        public TextView mTvMenu;
        public ImageView mImgIcon;
        public RelativeLayout mItemLayout;

        public ViewHolder(View v) {
            super(v);
            mTvMenu = v.findViewById(R.id.tv_menu);
            mImgIcon = v.findViewById(R.id.img_icon);
            mItemLayout = v.findViewById(R.id.item_layout);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MainMenuAdapter(Context context, List<MainMenuModel> myDataset, int layout) {
        mContext = context;
        mDataset = myDataset;
        mlayout = layout;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MainMenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(mlayout, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mImgIcon.setImageResource(mDataset.get(position).getIcon());
        holder.mTvMenu.setText(mDataset.get(position).getName());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}