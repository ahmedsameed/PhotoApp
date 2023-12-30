package com.example.ahmedjava;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;

import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CameraImagesLIst extends AppCompatActivity {
    private static final int REQUEST_PICK_IMAGE = 3;
    private List<Uri> imageList;
    private ImageAdapter imageAdapter;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_images_list);
        imageList = new ArrayList<Uri>();
        imageAdapter = new ImageAdapter(imageList);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(imageAdapter);
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);
       /* findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoentry();
            }
        });*/
    }

    public void openGallery(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_PICK_IMAGE);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        HashMap<String, Uri> imageMap = new HashMap<>();
        Log.d("OnActivityResult","OUT OF IF LOOP 63");
        if (requestCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            Log.d("OnActivityResult","First IF LOOP");
            if (selectedImageUri != null) {
                Log.d("OnActivityResult","SECOND IF LOOP");
                imageList.add(selectedImageUri);
                Log.d("OnActivityResult","imageList.add(selectedImageUri);70");
                imageMap.put("Ahmed", selectedImageUri);
                Log.d("OnActivityResult","imageMap.put(\"Ahmed\", selectedImageUri);");
                imageAdapter.notifyDataSetChanged();
                Call<Void> call = retrofitInterface.imageupdate(imageMap);
                Log.d("OnActivityResult", String.valueOf(imageMap.get("Ahmed")));
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.d("Response",response.message());
                        Log.d("Response", String.valueOf(response.code()));

                        if (response.code() == 200) {

                            Toast.makeText(CameraImagesLIst.this,
                                    "successfully", Toast.LENGTH_LONG).show();
                        } else if (response.code() == 404) {
                            Toast.makeText(CameraImagesLIst.this,
                                    "Failed", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(CameraImagesLIst.this, t.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });

            } else {
                Toast.makeText(this, "Failed to get image", Toast.LENGTH_SHORT).show();
            }
        }
    }

}