package com.example.suzukiapp.model;

import java.util.Date;

/**
 * Created by Lahiru on 3/30/2018.
 */

public class Car
{
    private String id;
    private String image;
    private String model;
    private int doorCount;
    private String colorsAvailable;
    private String fuelType;
    private double engineCapacity;
    private String transmission;
    private String releaseYear;
    private double price;
    private String features;

    //getters and setters

    public Car()
    {
    }
    public Car(String id, Double priceDouble)
    {
        this.id = id;
        this.price = price;
    }

    public Car(String id, String image, String model, int doorCount, String colorsAvailable, String fuelType, double engineCapacity, String transmission, String releaseYear, double price, String features)
    {
        this.id = id;
        this.image = image;
        this.model = model;
        this.doorCount = doorCount;
        this.colorsAvailable = colorsAvailable;
        this.fuelType = fuelType;
        this.engineCapacity = engineCapacity;
        this.transmission = transmission;
        this.releaseYear = releaseYear;
        this.price = price;
        this.features = features;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public String getModel()
    {
        return model;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public int getDoorCount()
    {
        return doorCount;
    }

    public void setDoorCount(int doorCount)
    {
        this.doorCount = doorCount;
    }

    public String getColorsAvailable()
    {
        return colorsAvailable;
    }

    public void setColorsAvailable(String colorsAvailable)
    {
        this.colorsAvailable = colorsAvailable;
    }

    public String getFuelType()
    {
        return fuelType;
    }

    public void setFuelType(String fuelType)
    {
        this.fuelType = fuelType;
    }

    public double getEngineCapacity()
    {
        return engineCapacity;
    }

    public void setEngineCapacity(double engineCapacity)
    {
        this.engineCapacity = engineCapacity;
    }

    public String getTransmission()
    {
        return transmission;
    }

    public void setTransmission(String transmission)
    {
        this.transmission = transmission;
    }

    public String getReleaseYear()
    {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear)
    {
        this.releaseYear = releaseYear;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public String getFeatures()
    {
        return features;
    }

    public void setFeatures(String features)
    {
        this.features = features;
    }
}
