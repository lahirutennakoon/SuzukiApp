package com.example.suzukiapp;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.suzukiapp.model.Car;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewCarsAdmin extends AppCompatActivity {

    ListView listViewCarsAll;

    DatabaseReference databaseReference;

    List<Car> carList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cars_admin);

        databaseReference = FirebaseDatabase.getInstance().getReference("Car");

        listViewCarsAll = (ListView) findViewById(R.id.listViewCars);

        carList = new ArrayList<>();

        listViewCarsAll.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Car car = carList.get(i);
                String carPriceG = Double.toString(car.getPrice());
                showUpdateDialog(car.getId(), carPriceG);

                return false;
            }
        });

    }



    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clear list if any previous list was there
                carList.clear();
                for(DataSnapshot carSnapshot: dataSnapshot.getChildren() ){
                    Car car = carSnapshot.getValue(Car.class);

                    carList.add(car);
                }

                CarListAdmin adapter = new CarListAdmin(ViewCarsAdmin.this, carList);
                listViewCarsAll.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    //for update view
    private void showUpdateDialog(final String carID, String carModel){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);


//        final TextView textViewModel = (TextView) dialogView.findViewById(R.id.textViewModel);
        final EditText editTextPrice = (EditText) dialogView.findViewById(R.id.editTextPrice);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.updateCarBtn);

        dialogBuilder.setTitle("Updating Price "+ carID);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPrice = editTextPrice.getText().toString().trim();

                if(TextUtils.isEmpty(newPrice)){
                    editTextPrice.setError("Price Required");
                    return;
                }
                updateCarPrice(carID,newPrice);
                alertDialog.dismiss();

            }
        });




    }

    private boolean updateCarPrice(String id, String price){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Car").child(id);

        Double priceDouble = Double.parseDouble(price);

        Car car = new Car(id, priceDouble);
        databaseReference.setValue(car);

        Toast.makeText(this, "Price Updated!", Toast.LENGTH_LONG).show();

        return true;
    }


}
