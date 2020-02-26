package com.otto.ottocash;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PinAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Callback callback;
    private int[] menu = new int[]{
            1, 2, 3,
            4, 5, 6,
            7, 8, 9,
            0, 0, R.drawable.backspace_white
    };

    public interface Callback {
        public void onUpdatePin(String digit);

        public void onDeletePin();
    }

    public PinAdapter(Callback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        if (viewType == 11 || viewType == 9) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pin_image, viewGroup, false);
            return new imageViewHolder(view);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pin_number, viewGroup, false);
            return new numberViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof numberViewHolder) {
            ((numberViewHolder) holder).textViewNumber.setText(menu[position] + "");
            ((numberViewHolder) holder).textViewNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onUpdatePin(menu[position] + "");
                }
            });
        } else if (holder instanceof imageViewHolder) {
            if (menu[position] == 0) {
                ((imageViewHolder) holder).imageViewItem.setVisibility(View.INVISIBLE);
                ((imageViewHolder) holder).imageViewItem.setEnabled(false);
            } else {
                ((imageViewHolder) holder).imageViewItem.setImageResource(menu[position]);
                ((imageViewHolder) holder).imageViewItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callback.onDeletePin();
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return 12;
    }


    public class numberViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textViewNumber)
        TextView textViewNumber;

        public numberViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class imageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageViewItem)
        ImageView imageViewItem;

        public imageViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
