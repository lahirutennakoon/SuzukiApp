package com.example.suzukiapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.suzukiapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class SignUpActivity extends AppCompatActivity
{
    //reference to firebase database (for textual data)
    private DatabaseReference databaseReference;

    private EditText editText;

    //a constant to track the file chooser intent
    private final int PICK_IMAGE_REQUEST = 234;

    //display selected image in ImageView
    private ImageView imageView;

    //a Uri object to store file path
    private Uri filePath;

    private ProgressBar progressBar;

    //file extension of image
    private String fileExtension;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
    }

    //select image on button click
    public void selectImage(View view)
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    //display selected image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            filePath = data.getData();
            try
            {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView = (ImageView)findViewById(R.id.userImage);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    //sign up user on button click
    public void signUp(View view)
    {
        //get an instance of DatabaseReference to write to table "User" in db
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");

        //create a user object
        final User user = new User();

        //get textbox values
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


        //upload image
        //reference to firebase storage (for multimedia data)
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

        //Create a storage reference from our app
        StorageReference storageReference = firebaseStorage.getReference();

        //fileextension of image
        fileExtension = filePath.toString().substring(filePath.toString().lastIndexOf("."));

        // Create a child reference
        // childRef now points to "User"
        StorageReference childRef = storageReference.child("User/" + email);

        //String image =
        user.setImage("image");

        //if there is an image to upload
        if (filePath != null)
        {
            //displaying a progress bar while upload is going on
            progressBar = (ProgressBar)findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);

            UploadTask uploadTask = childRef.putFile(filePath);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
            {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                {
                    //write text data to database if image upload is successful
                    databaseReference.push().setValue(user);
                    Toast.makeText(SignUpActivity.this, "Account created successfully!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }).addOnFailureListener(new OnFailureListener()
            {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                    Toast.makeText(SignUpActivity.this, "Failed to create account!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
        }





        //write text data to database
        /*databaseReference.push().setValue(user).addOnCompleteListener(new OnCompleteListener<Void>()
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
        });*/
    }
}
