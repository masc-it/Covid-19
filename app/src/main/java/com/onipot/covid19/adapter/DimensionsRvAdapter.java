package com.onipot.covid19.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.onipot.covid19.Dimension;
import com.onipot.covid19.R;

import java.util.List;

public class DimensionsRvAdapter  extends RecyclerView.Adapter<DimensionsRvAdapter.MyViewHolder>  {

    List<Dimension> dimensions;
    public DimensionsRvAdapter(List<Dimension> dimensions){
        this.dimensions = dimensions;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext())
                .inflate(R.layout.row_dimensions, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Dimension dim = dimensions.get(position);

        holder.tvTitle.setText(dim.getName());

        holder.tvTitle.setChecked(dim.isVisible());

    }

    @Override
    public int getItemCount() {
        return dimensions.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private CheckedTextView tvTitle;

        private MyViewHolder(View view) {

            super(view);

            tvTitle = view.findViewById(R.id.cb_dimension);

            tvTitle.setOnClickListener(view1 -> {

                dimensions.get(getAdapterPosition()).toggle();
                tvTitle.setChecked(!tvTitle.isChecked());
            });
        }
    }
}
