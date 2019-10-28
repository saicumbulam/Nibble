package com.example.nimble;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class AdminAddNewProductActivity extends AppCompatActivity {

    private String CategoryName, hAddress, hPhone, hname, saveCurrentDate, saveCurrentTime;
    private Button RegisterNewHotelButton;
    private ImageView InputHotelImage,InputFoodImage;
    private EditText InputHotelName, InputHotelAddress, InputHotelPhone, InputHotelId;
    private EditText InputFoodName, InputFoodDesc, InputFoodPrice;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private String downloadImageUrl;
    private StorageReference ProductImagesRef;
    private DatabaseReference ProductsRef;
    private ProgressDialog loadingBar;
    private Button RegisterNewFood;
    private String hid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CategoryName = getIntent().getExtras().get("category").toString();

        if (CategoryName.equals("hotel")) {
            setContentView(R.layout.activity_admin_add_new_hotel);

            ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Hotels");
            ProductsRef = FirebaseDatabase.getInstance().getReference().child("Hotels");


            RegisterNewHotelButton = (Button) findViewById(R.id.hotel_hotel_register_btn);
            InputHotelImage = (ImageView) findViewById(R.id.hotel_hotel_image);
            InputHotelName = (EditText) findViewById(R.id.hotel_hotel_name);
            InputHotelAddress = (EditText) findViewById(R.id.hotel_hotel_address);
            InputHotelPhone = (EditText) findViewById(R.id.hotel_hotel_phone);
            InputHotelId = (EditText) findViewById(R.id.hotel_hotel_id);
            loadingBar = new ProgressDialog(this);


            InputHotelImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    OpenGallery();
                }
            });


            RegisterNewHotelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    ValidateHotelData();
                }
            });

        } else if (CategoryName.equals("food")) {
            ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Food");
            ProductsRef = FirebaseDatabase.getInstance().getReference().child("Food");


            RegisterNewFood = (Button) findViewById(R.id.food_hotel_add);
            InputFoodImage = (ImageView) findViewById(R.id.food_hotel_image);
            InputFoodName = (EditText) findViewById(R.id.food_hotel_name);
            InputFoodDesc = (EditText) findViewById(R.id.food_hotel_desc);
            InputFoodPrice = (EditText) findViewById(R.id.food_hotel_price);
            InputHotelId = (EditText) findViewById(R.id.food_hotel_id);
            loadingBar = new ProgressDialog(this);


            InputFoodImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    OpenGallery();
                }
            });


            RegisterNewFood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    ValidateHotelData();
                }
            });
        }
    }

    private void OpenGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            ImageUri = data.getData();
            InputHotelImage.setImageURI(ImageUri);
        }
    }


    private void ValidateHotelData()
    {
        hAddress = InputHotelAddress.getText().toString();
        hPhone = InputHotelPhone.getText().toString();
        hname = InputHotelName.getText().toString();
        hid = InputHotelId.getText().toString();


        if (ImageUri == null)
        {
            Toast.makeText(this, "Product image is mandatory...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(hAddress))
        {
            Toast.makeText(this, "Please write Hotel Address...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(hPhone))
        {
            Toast.makeText(this, "Please write Hotel Phone...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(hname))
        {
            Toast.makeText(this, "Please write Hotel name...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StoreHotelInformation();
        }
    }



    private void StoreHotelInformation()
    {
        loadingBar.setTitle("Registering New Hotel");
        loadingBar.setMessage("Dear Admin, please wait while we are registering the new Hotel.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        final StorageReference filePath = ProductImagesRef.child(ImageUri.getLastPathSegment() + hid + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(AdminAddNewProductActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(AdminAddNewProductActivity.this, "Hotel Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if (task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(AdminAddNewProductActivity.this, "got the Hotel image Url Successfully...", Toast.LENGTH_SHORT).show();

                            SaveHotelInfoToDatabase();
                        }
                    }
                });
            }
        });
    }



    private void SaveHotelInfoToDatabase()
    {
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("hid", hid);
        productMap.put("hname", hname);
        productMap.put("haddress", hAddress);
        productMap.put("hphone", hPhone);


        ProductsRef.child(hid).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(AdminAddNewProductActivity.this, AdminCategoryActivity.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(AdminAddNewProductActivity.this, "Hotel registered successfully..", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(AdminAddNewProductActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
