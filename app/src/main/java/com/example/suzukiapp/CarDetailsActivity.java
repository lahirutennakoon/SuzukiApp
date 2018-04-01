package com.example.suzukiapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.suzukiapp.model.Car;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class CarDetailsActivity extends AppCompatActivity
{
    //reference to firebase database (for textual data)
    private DatabaseReference databaseReference;

    //reference to firebase storage (for images)
    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    private StorageReference storageReference;

    private TextView textView;

    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        //set app bar
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
        final String modelFromMainActivity = getIntent().getExtras().getString("model");
        System.out.println("Model: " + modelFromMainActivity);
        textView = (TextView) findViewById(R.id.model);
        textView.setText("Model: " + modelFromMainActivity);

        databaseReference.addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                Car car = (Car) dataSnapshot.getValue(Car.class);

                String modelFromDatabse = null;
                if (car != null)
                {
                    modelFromDatabse = car.getModel();
                }

                if (modelFromMainActivity != null && modelFromMainActivity.equals(modelFromDatabse))
                {
                    int doorCount = car.getDoorCount();
                    textView = (TextView) findViewById(R.id.doorCount);
                    textView.setText("Door Count: " + doorCount);

                    String colorsAvailable = car.getColorsAvailable();
                    textView = (TextView) findViewById(R.id.colorsAvailable);
                    textView.setText("Colors Available: " + colorsAvailable);

                    String fuelType = car.getFuelType();
                    textView = (TextView) findViewById(R.id.fuelType);
                    textView.setText("Fuel Type: " + fuelType);

                    double engineCapacity = car.getEngineCapacity();
                    textView = (TextView) findViewById(R.id.engineCapacity);
                    textView.setText("Engine Capacity (cc) : " + engineCapacity);

                    String transmission = car.getTransmission();
                    textView = (TextView) findViewById(R.id.transmission);
                    textView.setText("Transmission: " + transmission);

                    String releaseYear = car.getReleaseYear();
                    textView = (TextView) findViewById(R.id.releaseYear);
                    textView.setText("Release Year: " + releaseYear);

                    double price = car.getPrice();
                    textView = (TextView) findViewById(R.id.price);
                    textView.setText("Price(Rs.): " + price);

                    String features = car.getFeatures();
                    textView = (TextView) findViewById(R.id.features);
                    textView.setText("Features: " + features);


                    System.out.println("doorCount:" + doorCount);
                    System.out.println("colorsAvailable:" + colorsAvailable);
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)
            {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot)
            {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s)
            {

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });

        //download car image
        storageReference = firebaseStorage.getReference().child("Car/" + modelFromMainActivity);

        imageView = (ImageView) findViewById(R.id.carImageView);

        // Load the image using Glide
        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(storageReference)
                .into(imageView);


    }
}
