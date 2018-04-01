package com.example.suzukiapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.suzukiapp.model.Car;

import java.util.List;

/**
 * Created by Vimukthi Mudalige on 3/31/2018.
 */

public class CarListAdmin extends ArrayAdapter<Car> {

    private Activity context;
    private List<Car> carList;

    public CarListAdmin(Activity context, List<Car> carList){
        super(context, R.layout.list_layout_admin, carList);
        this.context = context;
        this.carList = carList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       // return super.getView(position, convertView, parent);

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout_admin,null,true);

        TextView textViewModel = (TextView) listViewItem.findViewById(R.id.textViewModel);
        TextView textViewPrice = (TextView) listViewItem.findViewById(R.id.textViewPrice);

        Car car = carList.get(position);
        textViewModel.setText(car.getModel());
        String gotprice = Double.toString(car.getPrice());
        textViewPrice.setText(gotprice);
//        textViewPrice.setText(car.getReleaseYear());

        return listViewItem;

    }
}
