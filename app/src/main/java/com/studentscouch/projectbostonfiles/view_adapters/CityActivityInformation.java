package com.studentscouch.projectbostonfiles.view_adapters;

import android.graphics.Bitmap;

import java.io.Serializable;

public class CityActivityInformation implements Serializable {

    private int
            price;
    private String
            address,
            name,
            UID,
            AID;

    private double rating;

    private Bitmap
            iconId,
            profileId;

    public CityActivityInformation(Bitmap iconId, Bitmap profileId, int price, String address, String name, double rating, String UID, String AID) {

        this.iconId = iconId;
        this.profileId = profileId;
        this.price = price;
        this.address = address;
        this.name = name;
        this.rating = rating;
        this.AID = AID;
        this.UID = UID;

    }

    Bitmap getIconId() {
        return iconId;
    }

    Bitmap getProfileId() {
        return profileId;
    }


    public int getPrice() {
        return price;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    public String getAID(){return AID;}

    public String getUID(){return UID;}

}
