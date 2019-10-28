package com.example.nimble.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.nimble.Interface.ItemClickListner;
import com.example.nimble.R;


public class HotelViewHolder extends RecyclerView.ViewHolder {
    public TextView txtHotelName, txtHotelAddress, txtHotelPhone;


    public HotelViewHolder(View itemView)
    {
        super(itemView);

        txtHotelName = (TextView) itemView.findViewById(R.id.hotel_name);
        txtHotelAddress = (TextView) itemView.findViewById(R.id.hotel_address);
        txtHotelPhone = (TextView) itemView.findViewById(R.id.hotel_phone);
    }
}