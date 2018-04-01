package com.example.suzukiapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminPanelActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private TextView welcomeTextText;
    private Button buttonLogout;
    private Button testButton;
    private Button buttonViewAll;



    FirebaseDatabase database;
    DatabaseReference myRef;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        firebaseAuth = FirebaseAuth.getInstance();
//        database = FirebaseDatabase.getInstance();
//        myRef=database.getReference("Cars");

       // myRef.setValue("Hello");
        //check if user is logged in
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }


        welcomeTextText = (TextView) findViewById(R.id.welcomeText);

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        assert firebaseUser != null;
        welcomeTextText.setText(firebaseUser.getEmail());

        buttonLogout = (Button) findViewById(R.id.logOutBtn);
//test button
        testButton = (Button) findViewById(R.id.testBtn);

        buttonViewAll = (Button) findViewById(R.id.viewAllCarsBtn);

        buttonLogout.setOnClickListener(this);

        testButton.setOnClickListener(this);

        buttonViewAll.setOnClickListener(this);

    }

    public void testWriting(){

       // Toast.makeText(AdminPanelActivity.this, "Test method running!", Toast.LENGTH_LONG).show();
        // Write a message to the database


        myRef.setValue("Hello, World!").addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(AdminPanelActivity.this, "yay!", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(AdminPanelActivity.this, "Something wrong!" + task.getException().getMessage(), Toast.LENGTH_LONG).show();

                }

            }
        });


    }


    //add new car button click
    public void addCar(View view)
    {
        Toast.makeText(AdminPanelActivity.this, "Add a New Car!", Toast.LENGTH_LONG).show();

        Intent intentAddCar = new Intent(this, AddCarActivity.class);
        startActivity(intentAddCar);

    }

    @Override
    public void onClick(View view) {

        //log out the logged in user
        if(view == buttonLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        if(view == testButton){
            testWriting();
        }

        if(view == buttonViewAll){
            Intent intentViewAllCar = new Intent(this, ViewCarsAdmin.class);
            startActivity(intentViewAllCar);
        }
    }
}
