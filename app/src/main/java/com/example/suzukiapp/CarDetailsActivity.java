package com.example.suzukiapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CarDetailsActivity extends AppCompatActivity
{
    //reference to firebase database (for textual data)
    private DatabaseReference databaseReference;

    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        //set app bar for login page
        Toolbar toolbar = (Toolbar) findViewById(R.id.login_app_bar);
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar actionBar = getSupportActionBar();

        // Enable the Up button
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);


        //get an instance of DatabaseReference to read from table "Car" in db
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Car");

        //get previous activity's clicked item to text view
        String model = getIntent().getExtras().getString("model");
        System.out.println("Model: " + model);
        textView = (TextView) findViewById(R.id.carDetails);
        textView.setText(model);
    }
}
