package com.studentscouch.projectbostonfiles.observables;

import android.graphics.Bitmap;
import android.net.Uri;

import java.util.Observable;

public class ApartmentEditActivityObservables extends Observable {

    private int
            original_tariff,
            original_num_people;

    private int dotsCount;

    private String
            locationID,
            AID;

    private String
            apartement1String = "",
            apartement2String = "",
            apartement3String = "";

    private String
            descriptionFromIntent,
            titleFromIntent;

    private String
            price_per_night,
            isTwoPeopleAllowed;

    private String
            desciptionTitleString,
            descriptionDescriptionString,
            numPeoplePerStayString,
            pricePerNightString;

    private String
            encodedImage,
            encodedImage2,
            encodedImage3;

    private Bitmap[] image_resources_images;

    private Uri mCapturedImageURI;

    public int getOriginal_num_people() {
        return original_num_people;
    }

    public int getOriginal_tariff() {
        return original_tariff;
    }

    public String getLocationID() {
        return locationID;
    }

    public int getDotsCount() {
        return dotsCount;
    }

    public String getApartement1String() {
        return apartement1String;
    }

    public String getApartement3String() {
        return apartement3String;
    }

    public String getApartement2String() {
        return apartement2String;
    }

    public String getDescriptionFromIntent() {
        return descriptionFromIntent;
    }

    public String getPrice_per_night() {
        return price_per_night;
    }

    public String getTitleFromIntent() {
        return titleFromIntent;
    }

    public String getDesciptionTitleString() {
        return desciptionTitleString;
    }

    public String getDescriptionDescriptionString() {
        return descriptionDescriptionString;
    }

    public String getNumPeoplePerStayString() {
        return numPeoplePerStayString;
    }

    public String getPricePerNightString() {
        return pricePerNightString;
    }

    public String getEncodedImage() {
        return encodedImage;
    }

    public String getEncodedImage2() {
        return encodedImage2;
    }

    public String getEncodedImage3() {
        return encodedImage3;
    }

    public String getIsTwoPeopleAllowed() {
        return isTwoPeopleAllowed;
    }

    public String getAID() {
        return AID;
    }

    public Uri getmCapturedImageURI() {
        return mCapturedImageURI;
    }

    public Bitmap[] getImage_resources_images() {
        return image_resources_images;
    }

    public void setOriginal_tariff(int original_tariff) {
        this.original_tariff = original_tariff;
        this.setChanged();
        this.notifyObservers(original_tariff);
    }

    public void setOriginal_num_people(int original_num_people) {
        this.original_num_people = original_num_people;
        this.setChanged();
        this.notifyObservers(original_num_people);
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
        this.setChanged();
        this.notifyObservers(locationID);
    }

    public void setDotsCount(int dotsCount) {
        this.dotsCount = dotsCount;
        this.setChanged();
        this.notifyObservers(dotsCount);
    }

    public void setApartement1String(String apartement1String) {
        this.apartement1String = apartement1String;
        this.setChanged();
        this.notifyObservers(apartement1String);
    }

    public void setApartement2String(String apartement2String) {
        this.apartement2String = apartement2String;
        this.setChanged();
        this.notifyObservers(apartement2String);
    }

    public void setApartement3String(String apartement3String) {
        this.apartement3String = apartement3String;
        this.setChanged();
        this.notifyObservers(apartement3String);
    }

    public void setDescriptionFromIntent(String descriptionFromIntent) {
        this.descriptionFromIntent = descriptionFromIntent;
        this.setChanged();
        this.notifyObservers(descriptionFromIntent);
    }

    public void setTitleFromIntent(String titleFromIntent) {
        this.titleFromIntent = titleFromIntent;
        this.setChanged();
        this.notifyObservers(titleFromIntent);
    }

    public void setPrice_per_night(String price_per_night) {
        this.price_per_night = price_per_night;
        this.setChanged();
        this.notifyObservers(price_per_night);
    }

    public void setIsTwoPeopleAllowed(String isTwoPeopleAllowed) {
        this.isTwoPeopleAllowed = isTwoPeopleAllowed;
        this.setChanged();
        this.notifyObservers(isTwoPeopleAllowed);
    }

    public void setAID(String AID) {
        this.AID = AID;
        this.setChanged();
        this.notifyObservers(AID);
    }

    public void setDesciptionTitleString(String desciptionTitleString) {
        this.desciptionTitleString = desciptionTitleString;
        this.setChanged();
        this.notifyObservers(desciptionTitleString);
    }

    public void setDescriptionDescriptionString(String descriptionDescriptionString) {
        this.descriptionDescriptionString = descriptionDescriptionString;
        this.setChanged();
        this.notifyObservers(descriptionDescriptionString);
    }

    public void setNumPeoplePerStayString(String numPeoplePerStayString) {
        this.numPeoplePerStayString = numPeoplePerStayString;
        this.setChanged();
        this.notifyObservers(numPeoplePerStayString);
    }

    public void setPricePerNightString(String pricePerNightString) {
        this.pricePerNightString = pricePerNightString;
        this.setChanged();
        this.notifyObservers(pricePerNightString);
    }

    public void setEncodedImage(String encodedImage) {
        this.encodedImage = encodedImage;
        this.setChanged();
        this.notifyObservers(encodedImage);
    }

    public void setEncodedImage2(String encodedImage2) {
        this.encodedImage2 = encodedImage2;
        this.setChanged();
        this.notifyObservers(encodedImage2);
    }

    public void setEncodedImage3(String encodedImage3) {
        this.encodedImage3 = encodedImage3;
        this.setChanged();
        this.notifyObservers(encodedImage3);
    }

    public void setImage_resources_images(Bitmap[] image_resources_images) {
        this.image_resources_images = image_resources_images;
        this.setChanged();
        this.notifyObservers(image_resources_images);
    }

    public void setmCapturedImageURI(Uri mCapturedImageURI) {
        this.mCapturedImageURI = mCapturedImageURI;
        this.setChanged();
        this.notifyObservers(mCapturedImageURI);
    }
}
