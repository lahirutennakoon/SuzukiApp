package com.example.suzukiapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.suzukiapp.model.Car;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class AddCarActivity extends AppCompatActivity {


    //to connect to firebase database
    private DatabaseReference databaseReference;

    private EditText carModelx;
    private EditText doorCount;
    private EditText colorsAvailablex;
    private EditText engineCapacity;
    private EditText releaseYearx;
    private EditText carPrice;
    private EditText carFeatures;
    private EditText carImage;
    private Spinner editSpinner;

    private Button buttonAddNCar;

    private ProgressBar progressBar;

    //a constant to track the file chooser intent
    private final int PICK_IMAGE_REQUEST = 234;

    //display selected image in ImageView
    private ImageView imageView;

    //a Uri object to store file path
    private Uri filePath;

    //file extension of image
    private String fileExtension;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);


        //new
        //get an instance of DatabaseReference
        databaseReference = FirebaseDatabase.getInstance().getReference("Car"); //added s


        buttonAddNCar = (Button) findViewById(R.id.addCar);

        buttonAddNCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewCarDetails();
            }
        });


    }

    public void selectImage(View view)
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    //set image thumbnail
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
                imageView = (ImageView)findViewById(R.id.carImage);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


    public void addNewCarDetails(){

        //get input values
        carModelx = (EditText) findViewById(R.id.carModel);
        String carModel = carModelx.getText().toString().trim();

        doorCount = (EditText) findViewById(R.id.doorCount);
        String doorCountString = doorCount.getText().toString().trim();
        int doorCount = Integer.parseInt(doorCountString);


        colorsAvailablex = (EditText) findViewById(R.id.colorsAvailable);
        String colorsAvailable = colorsAvailablex.getText().toString().trim();


        engineCapacity = (EditText) findViewById(R.id.engineCapacity);
        String engineCapacityString = engineCapacity.getText().toString().trim();
        double engineCapacity = Double.parseDouble(engineCapacityString);


        Spinner spinner = (Spinner)findViewById(R.id.transmissionType);
        String transmissionType = spinner.getSelectedItem().toString().trim();

        Spinner transmissionT = (Spinner)findViewById(R.id.fuelType);
        String fuelType = transmissionT.getSelectedItem().toString().trim();

//        editText = (editSpinner) findViewById(R.id.transmissionType);
//        String transmissionType = editText.getText().toString().trim();

        releaseYearx = (EditText) findViewById(R.id.releaseYear);
        String releaseYear = releaseYearx.getText().toString().trim();


        carPrice = (EditText) findViewById(R.id.carPrice);
        String carPriceString = carPrice.getText().toString().trim();
        double carPrice = Double.parseDouble(carPriceString);


        carFeatures = (EditText) findViewById(R.id.carFeatures);
        String featuresCar = carFeatures.getText().toString().trim();


        if(TextUtils.isEmpty(carModel)){
            Toast.makeText(this,"Please enter your carModel",Toast.LENGTH_LONG).show();
            return;
        }
        else
        {
            final String id = databaseReference.push().getKey();
            String tempimg = "tempimage";

//             Car car = new Car(id, tempimg, carModel, doorCount, colorsAvailable, engineCapacity, transmissionType, releaseYear, carPrice);


//            try
{

                final Car car = new Car(id, tempimg, carModel, doorCount, colorsAvailable, fuelType, engineCapacity, transmissionType, releaseYear, carPrice,featuresCar);

        car.setModel(carModel);
        car.setDoorCount(doorCount);
        car.setColorsAvailable(colorsAvailable);
        car.setFuelType(fuelType);
        car.setEngineCapacity(engineCapacity);
        car.setTransmission(transmissionType);
        car.setReleaseYear(releaseYear);
        car.setPrice(carPrice);
        car.setFeatures(featuresCar);






                //if there is an image to upload
                if (filePath != null)
                {
                    //add image
                    //reference to firebase storage (for multimedia data)
                    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

                    //Create a storage reference from our app
                    StorageReference storageReference = firebaseStorage.getReference();

                    //file extension of image
                    fileExtension = filePath.toString().substring(filePath.toString().lastIndexOf("."));

                    // Create a child reference
                    StorageReference childRef = storageReference.child("Car/" + carModel);


                    car.setImage(carModel);
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
                           // databaseReference.push().setValue(car);


                            databaseReference.child(id).setValue(car);
                            Toast.makeText(AddCarActivity.this, "New Car Added!", Toast.LENGTH_LONG).show();
                            //Toast.makeText(AddCarActivity.this, "success!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.INVISIBLE);

                            //return back to admin panel after adding the car
                            finish();
                            startActivity(new Intent(getApplicationContext(), AdminPanelActivity.class));

                        }
                    }).addOnFailureListener(new OnFailureListener()
                    {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            Toast.makeText(AddCarActivity.this, "Failed!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }
                else
                {
                    car.setImage("no_image");
                    databaseReference.child(id).setValue(car);
                    Toast.makeText(AddCarActivity.this, "New Car Added!", Toast.LENGTH_LONG).show();

                    //Toast.makeText(AddCarActivity.this, "success!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    //return back to admin panel after adding the car
                    finish();
                    startActivity(new Intent(getApplicationContext(), AdminPanelActivity.class));
                }











//

        }
//        catch (Exception e){
//            Toast.makeText(AddCarActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
//
//        }


        }
    }








    //sign up car on button click
//    public void addNewCar(View view) {
//
//
//
//
//
////        databaseReference = FirebaseDatabase.getInstance().getReference().child("Cars"); //added s
//
//        //create a Car object
//        final Car car = new Car();
//
//        //get input values
//        editText = (EditText) findViewById(R.id.carModel);
//        String carModel = editText.getText().toString().trim();
//        car.setModel(carModel);
//
//        editText = (EditText) findViewById(R.id.doorCount);
//        String doorCountString = editText.getText().toString().trim();
//        int doorCount = Integer.parseInt(doorCountString);
//        car.setDoorCount(doorCount);
//
//        editText = (EditText) findViewById(R.id.colorsAvailable);
//        String colorsAvailable = editText.getText().toString().trim();
//        car.setColorsAvailable(colorsAvailable);
//
//        editText = (EditText) findViewById(R.id.engineCapacity);
//        String engineCapacityString = editText.getText().toString().trim();
//        double engineCapacity = Double.parseDouble(engineCapacityString);
//        car.setEngineCapacity(engineCapacity);
//
//        editText = (EditText) findViewById(R.id.transmissionType);
//        String transmissionType = editText.getText().toString().trim();
//        car.setTransmission(transmissionType);
//
//        editText = (EditText) findViewById(R.id.releaseYear);
//        String releaseYear = editText.getText().toString().trim();
//        car.setReleaseYear(releaseYear);
//
//        editText = (EditText) findViewById(R.id.carPrice);
//        String carPriceString = editText.getText().toString().trim();
//        double carPrice = Double.parseDouble(carPriceString);
//        car.setPrice(carPrice);
//
//        Toast.makeText(AddCarActivity.this, "add car method called!", Toast.LENGTH_LONG).show();
//
//
////        try {
////            car.setImage("myimg");
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//
//
////        //uploading the new car image
////
////        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
////
////        //Create a storage reference from our app
////        StorageReference storageReference = firebaseStorage.getReference();
////
////        //fileextension of image
////        fileExtension = filePath.toString().substring(filePath.toString().lastIndexOf("."));
////
////        // Create a child reference
////        StorageReference childRef = storageReference.child("Car/" + carModel);
////
////        car.setImage("image");
////
////        //if there is an image to upload (filePath != null)
////        if (true)
////        {
////            //displaying a progress bar while upload is going on
////            progressBar = (ProgressBar)findViewById(R.id.progressBar);
////            progressBar.setVisibility(View.VISIBLE);
////
////            UploadTask uploadTask = childRef.putFile(filePath);
////
////            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
////            {
////                @Override
////                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
////                {
////                    //write text data to database
////                    databaseReference.push().setValue(car);
////
////                    progressBar.setVisibility(View.INVISIBLE);
////                }
////            }).addOnFailureListener(new OnFailureListener()
////            {
////                @Override
////                public void onFailure(@NonNull Exception e)
////                {
////                    Toast.makeText(AddCarActivity.this, "Failed to add new car!", Toast.LENGTH_LONG).show();
////                    progressBar.setVisibility(View.INVISIBLE);
////                }
////            });
////        }
//
//
//    }

    }
