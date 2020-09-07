package com.otto.sdk.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.otto.sdk.R;
import com.otto.sdk.model.general.TopUpGuide;
import com.otto.sdk.ui.activity.topup.TopUpActivity;

import java.util.List;

import static com.otto.sdk.ui.component.support.UiUtil.getHTMLContent;

public class TopUpGuideAdapter extends RecyclerView.Adapter<TopUpGuideAdapter.ViewHolder> {

    private Context mContext;
    public List<TopUpGuide> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvName;
        public TextView tvDesc;
        public ImageView imgLogo;
        public ImageView imgArrow;
        public LinearLayout itemLayout;
        public LinearLayout toolContainer;



        public ViewHolder(View v) {
            super(v);
            tvName = (TextView) v.findViewById(R.id.tv_menu);
            tvDesc = (TextView) v.findViewById(R.id.tv_desc);
            imgLogo = (ImageView) v.findViewById(R.id.img_logo);
            imgArrow = (ImageView) v.findViewById(R.id.img_arrow);
            itemLayout = (LinearLayout) v.findViewById(R.id.item_layout);
            toolContainer = (LinearLayout) v.findViewById(R.id.tool_container);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TopUpGuideAdapter(Context context, List<TopUpGuide> myDataset) {
        mContext = context;
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TopUpGuideAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_top_up_guide, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        TopUpGuide item = mDataset.get(position);

        Glide.with(mContext)
                .load(mDataset.get(position).getLogo())
                .placeholder(R.drawable.white_grey_border_rounded_bg)
                .error(R.drawable.white_grey_border_rounded_bg)
                .into(holder.imgLogo);

        holder.imgArrow.setRotation(90);

        holder.tvName.setText(mDataset.get(position).getName());
        holder.tvDesc.setText(mDataset.get(position).getDesc());

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Remove All Item Inside this View
        holder.toolContainer.removeAllViews();
        holder.toolContainer.setVisibility(View.GONE);

        for (final TopUpGuide.Tool tool : mDataset.get(position).getToolList()) {
            View view = layoutInflater.inflate(R.layout.item_top_up_guide_tool, null);

            TextView tvName = (TextView) view.findViewById(R.id.tv_name);
            LinearLayout itemToolLayout = (LinearLayout) view.findViewById(R.id.item_tool_layout);
            final ImageView imgToolArrow = (ImageView) view.findViewById(R.id.img_tool_arrow);
            final LinearLayout stepContainer = (LinearLayout) view.findViewById(R.id.step_container);

            //Remove All Item Inside this View
            stepContainer.removeAllViews();
            stepContainer.setVisibility(View.GONE);

            int i = 1;
            for (String step : tool.getStepList()) {
                View viewStep = layoutInflater.inflate(R.layout.item_top_up_guide_step, null);

                TextView tvNo = (TextView) viewStep.findViewById(R.id.tv_no);
                TextView tvStep = (TextView) viewStep.findViewById(R.id.tv_step);
                TextView tvNote = (TextView) viewStep.findViewById(R.id.tv_note);
                LinearLayout itemStepLayout = (LinearLayout) viewStep.findViewById(R.id.item_step_layout);

                imgToolArrow.setRotation(90);
                tvNo.setText(String.valueOf(i));
                tvStep.setText(getHTMLContent(step));

                if (!tool.getNote().equals("") && (tool.getStepList().size() == (i))) {
                    tvNote.setText(tool.getNote());
                    tvNote.setVisibility(View.VISIBLE);
                } else {
                    tvNote.setVisibility(View.GONE);
                }

                stepContainer.addView(viewStep);

                i++;
            }

            tvName.setText(tool.getName());
            holder.toolContainer.addView(view);

            //Validate Child View
            if (tool.isSelected()) {
                imgToolArrow.setRotation(-90);
                stepContainer.setVisibility(View.VISIBLE);
            } else {
                imgToolArrow.setRotation(90);
                stepContainer.setVisibility(View.GONE);
            }

            itemToolLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((TopUpActivity) mContext).childSelected(position, tool.getPosition());
                }
            });
        }

        //Validate Parent View
        if (item.isSelected()) {
            holder.imgArrow.setRotation(-90);
            holder.toolContainer.setVisibility(View.VISIBLE);
        } else {
            holder.imgArrow.setRotation(90);
            holder.toolContainer.setVisibility(View.GONE);
        }

        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TopUpActivity) mContext).parentSelected(position);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}