package com.example.nimble;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class ScannerHeadActivity extends AppCompatActivity {

    private TextView scan_btn;
    private TextView skip_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            this.getSupportActionBar().hide();
        } catch (Exception e){
            e.printStackTrace();
        }

        setContentView(R.layout.activity_scanner_head);


        scan_btn = findViewById(R.id.btn_scan);
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoScanCodeActivity();
            }
        });

        skip_btn = findViewById(R.id.skipnowBtn);
        skip_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMainActivity();
            }
        });
    }


    public void gotoScanCodeActivity() {
        Intent intent = new Intent(this, ScanCodeActivity.class);
        startActivity(intent);
    }

    public void gotoMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
