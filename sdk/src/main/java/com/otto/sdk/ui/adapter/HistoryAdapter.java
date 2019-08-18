package com.otto.sdk.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.otto.sdk.R;
import com.otto.sdk.model.api.response.TransactionHistoryResponse;
import com.otto.sdk.support.UiUtil;
import com.otto.sdk.ui.component.support.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
    private Context context;
    private List<TransactionHistoryResponse.DataBean.TransactionBean.HistoriesBean> listOfData = new ArrayList<TransactionHistoryResponse.DataBean.TransactionBean.HistoriesBean>();

    public HistoryAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_history, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        TransactionHistoryResponse.DataBean.TransactionBean.HistoriesBean data = listOfData.get(i);

        switch (data.getTransactionType()){
            case "D":
                holder.ivIcon.setImageResource(R.drawable.ic_history_red);
                holder.tvPrice.setTextColor(context.getResources().getColor(R.color.Red_d04a55));
                break;
            case "C":
                holder.ivIcon.setImageResource(R.drawable.ic_history_green);
                holder.tvPrice.setTextColor(context.getResources().getColor(R.color.Green_0CB04A));

                break;
        }

        holder.tvOderId.setText(data.getId());
        holder.tvDate.setText(DateUtil.formatDateToString(data.getValueDate(),
                "dd MM yyyy hh:mm:ss","dd/MM/yyyy"));
        holder.tvTime.setText(DateUtil.formatDateToString(data.getValueDate(),
                "dd MM yyyy hh:mm:ss","hh:mm:ss"));
        holder.tvPrice.setText(UiUtil.formatMoneyIDR(Long.valueOf(data.getAmount())));
        holder.tvDesc.setText(data.getDescription());
    }

    @Override
    public int getItemCount() {
        return listOfData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvPrice;
        TextView tvDesc;
        TextView tvDate;
        TextView tvOderId;
        TextView tvTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvDesc = itemView.findViewById(R.id.tvDescription);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvOderId = itemView.findViewById(R.id.tvOrderID);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }

    public void setData(List<TransactionHistoryResponse.DataBean.TransactionBean.HistoriesBean> data) {
        listOfData.clear();
        listOfData = data;
        notifyDataSetChanged();
    }
}
