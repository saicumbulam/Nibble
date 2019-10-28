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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nimble.Models.Foods;
import com.example.nimble.Models.Hotels;
import com.example.nimble.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FoodItemsActivity extends AppCompatActivity {

    private DatabaseReference FoodRef;
    private RecyclerView recyclerView;
    private String hotelId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_items);


        hotelId = getIntent().getStringExtra("hid");
        updateHotelNameInActionBar(hotelId);


        FoodRef = FirebaseDatabase.getInstance().getReference().child("Food").child(hotelId);

        recyclerView = findViewById(R.id.food_listview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodItemsActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateHotelNameInActionBar (final String hotelId) {

        final Hotels[] hotel = new Hotels[1];
        DatabaseReference hotelsRef = FirebaseDatabase.getInstance().getReference().child("Hotels").child(hotelId);
        hotelsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                hotel[0] = snapshot.getValue(Hotels.class);
                if (hotel[0] != null && hotel[0].getHid() != null) {
                    ActionBar actionBar = getSupportActionBar();
                    actionBar.setTitle("Today's " + hotel[0].getHname() + " Menu");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Foods> options =
                new FirebaseRecyclerOptions.Builder<Foods>()
                        .setQuery(FoodRef, Foods.class)
                        .build();


        FirebaseRecyclerAdapter<Foods, FoodViewHolder> adapter =
                new FirebaseRecyclerAdapter<Foods, FoodViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull FoodViewHolder holder, int position, @NonNull final Foods model)
                    {
                        holder.txtFoodName.setText(model.getFname());
                        holder.txtFoodPrice.setText("Price = $" + String.valueOf(model.getFprice()));

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(FoodItemsActivity.this, ProductDetailsActivity.class);
                                intent.putExtra("hid", hotelId);
                                intent.putExtra("fooditem", model.getFname());
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_items_layout, parent, false);
                        FoodViewHolder holder = new FoodViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
