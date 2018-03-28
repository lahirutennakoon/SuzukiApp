package com.example.suzukiapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.suzukiapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity
{

    private DatabaseReference databaseReference;

    private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    //sign up user on button click
    public void signUp(View view)
    {
        //get an instance of DatabaseReference to write to table "User" in db
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");

        //create a user object
        User user = new User();

        //get textbox values
        //String image =
        user.setImage("image");

        editText = (EditText)findViewById(R.id.firstName);
        String firstName = editText.getText().toString().trim();
        user.setFirstName(firstName);

        editText = (EditText)findViewById(R.id.lastName);
        String lastName = editText.getText().toString().trim();
        user.setLastName(lastName);

        editText = (EditText)findViewById(R.id.phone);
        String phone = editText.getText().toString().trim();
        user.setPhone(phone);

        editText = (EditText)findViewById(R.id.email);
        String email = editText.getText().toString().trim();
        user.setEmail(email);

        editText = (EditText)findViewById(R.id.password);
        String password = editText.getText().toString().trim();
        user.setPassword(password);

        //write to database
        databaseReference.push().setValue(user).addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if(task.isSuccessful())
                {
                    Toast.makeText(SignUpActivity.this, "Account created successfully!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(SignUpActivity.this, "Failed to create account!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
