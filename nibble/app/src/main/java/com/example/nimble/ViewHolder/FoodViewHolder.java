package com.example.nimble.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.nimble.R;


public class FoodViewHolder extends RecyclerView.ViewHolder {
    public TextView txtFoodName, txtFoodPrice;


    public FoodViewHolder(View itemView)
    {
        super(itemView);

        txtFoodName = (TextView) itemView.findViewById(R.id.food_name);
        txtFoodPrice = (TextView) itemView.findViewById(R.id.food_price);
    }
}