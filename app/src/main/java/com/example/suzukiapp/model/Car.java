package com.example.suzukiapp.model;

import java.util.Date;

/**
 * Created by Lahiru on 3/30/2018.
 */

public class Car
{
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
