package com.example.nimble;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class AdminCategoryActivity extends AppCompatActivity {

    private ImageView hotel, food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        hotel = (ImageView) findViewById(R.id.hotel);
        food = (ImageView) findViewById(R.id.food);

        hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "hotel");
                startActivity(intent);
            }
        });


        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(AdminCategoryActivity.this, "Feature to be Implemented", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
//                intent.putExtra("category", "food");
//                startActivity(intent);
            }
        });
    }
}
