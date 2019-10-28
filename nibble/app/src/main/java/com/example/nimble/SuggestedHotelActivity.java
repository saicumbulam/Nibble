package com.example.nimble;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nimble.Models.Hotels;
import com.example.nimble.ViewHolder.HotelViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class SuggestedHotelActivity extends AppCompatActivity {


    private DatabaseReference HotelsRef;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_hotel);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Suggested Hotels");
        actionBar.setDisplayHomeAsUpEnabled(true);

        HotelsRef = FirebaseDatabase.getInstance().getReference().child("Hotels");

        recyclerView = findViewById(R.id.suggested_hotel_listview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Hotels> options =
                new FirebaseRecyclerOptions.Builder<Hotels>()
                        .setQuery(HotelsRef, Hotels.class)
                        .build();


        FirebaseRecyclerAdapter<Hotels, HotelViewHolder> adapter =
                new FirebaseRecyclerAdapter<Hotels, HotelViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull HotelViewHolder holder, int position, @NonNull final Hotels model)
                    {
                        holder.txtHotelName.setText(model.getHname());
                        holder.txtHotelAddress.setText("Address = " + model.getHaddress());
                        holder.txtHotelPhone.setText("Phone = " + String.valueOf(model.getHphone()));

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(SuggestedHotelActivity.this, FoodItemsActivity.class);
                                intent.putExtra("hid", model.getHid());
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_items_layout, parent, false);
                        HotelViewHolder holder = new HotelViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
