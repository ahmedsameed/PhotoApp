package com.example.ahmedjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameentry();
            }
        });
        //onCreate2();
    }


    private void nameentry() {


    Button button3 = findViewById(R.id.button3);
    final EditText name = findViewById(R.id.editTextText2);
    button3.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            HashMap<String, String> map = new HashMap<>();

            map.put("name", name.getText().toString());
            Call<Void> call = retrofitInterface.executeupdate(map);
            Log.d("MyTag", "Here we are"+ name.getText().toString());
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Log.d("Response",response.message());
                    Log.d("Response", String.valueOf(response.code()));

                    if (response.code() == 200) {

                        Toast.makeText(MainActivity.this,
                                "successfully", Toast.LENGTH_LONG).show();
                    } else if (response.code() == 404) {
                        Toast.makeText(MainActivity.this,
                                "Failed", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(MainActivity.this, t.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });

        }
    });

    }
}

   /* public void onCreate2(){
        Button b=  findViewById(R.id.button);
        b.setText("Ahmed");
        TextView textView = (TextView) findViewById(R.id.editTextText);
        textView.setText("Hello, to Homepage!");

    }
        public void onTap(View view){
            Intent nextpage = new Intent(this,CameraImagesLIst.class);
            startActivity(nextpage);
       /* Button b=  (Button) view;
       // b.setText("Yoo");
        TextView textView = (TextView) findViewById(R.id.editTextText);
        textView.setText("Hello, Boy");*/





