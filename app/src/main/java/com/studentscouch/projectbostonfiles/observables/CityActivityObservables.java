package com.studentscouch.projectbostonfiles.observables;

import android.graphics.Bitmap;

import java.util.Observable;

public class CityActivityObservables extends Observable {

    private String placeID;
    private String cityName;
    private String countryName;

    private Bitmap image;
    private Bitmap image2;

    private boolean isLoaded;

    public String getCountryName() {
        return countryName;
    }

    public String getCityName() {
        return cityName;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getPlaceID() {
        return placeID;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public Bitmap getImage2() {
        return image2;
    }


    public void setPlaceID(String placeID) {
        this.placeID = placeID;
        this.setChanged();
        this.notifyObservers(placeID);
    }

    public void setImage(Bitmap image) {
        this.image = image;
        this.setChanged();
        this.notifyObservers(image);
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
        this.setChanged();
        this.notifyObservers(cityName);
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
        this.setChanged();
        this.notifyObservers(countryName);
    }

    public void setLoaded(boolean loaded) {
        isLoaded = loaded;
        this.setChanged();
        this.notifyObservers(loaded);
    }
    public void setImage2(Bitmap image2) {
        this.image2 = image2;
        this.setChanged();
        this.notifyObservers(image2);
    }
}
