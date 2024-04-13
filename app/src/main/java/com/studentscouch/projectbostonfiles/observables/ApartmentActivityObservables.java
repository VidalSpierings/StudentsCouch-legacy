package com.studentscouch.projectbostonfiles.observables;

import java.util.Observable;

public class ApartmentActivityObservables extends Observable {

    private String city_or_village;
    private String description;
    private String house_number;
    private String locationID;
    private String street;
    private String title;
    private String UID;
    private String AID;

    private String hostUID;

    private String IBAN;

    private String apartment1String;
    private String apartment2String;
    private String apartment3String;

    private int numRatings;
    private int isTwoPeopleAllowed;
    private int price_per_night;
    private int numSubleasedNightsLeft = 123456789;

    private double ratingAverage;

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
        this.setChanged();
        this.notifyObservers(IBAN);
    }

    public void setNumSubleasedNightsLeft(int numSubleasedNightsLeft) {
        this.numSubleasedNightsLeft = numSubleasedNightsLeft;
        this.setChanged();
        this.notifyObservers(numSubleasedNightsLeft);
    }

    public void setHostUID(String hostUID) {
        this.hostUID = hostUID;
        this.setChanged();
        this.notifyObservers(hostUID);
    }

    public void setCity_or_village(String city_or_village) {
        this.city_or_village = city_or_village;
        this.setChanged();
        this.notifyObservers(city_or_village);
    }

    public void setDescription(String description) {
        this.description = description;
        this.setChanged();
        this.notifyObservers(description);
    }

    public void setHouse_number(String house_number) {

        this.house_number = house_number;
        this.setChanged();
        this.notifyObservers(house_number);
    }

    public void setLocationID(String locationID) {

        this.locationID = locationID;
        this.setChanged();
        this.notifyObservers(locationID);
    }

    public void setStreet(String street) {

        this.street = street;
        this.setChanged();
        this.notifyObservers(street);
    }

    public void setTitle(String title) {

        this.title = title;
        this.setChanged();
        this.notifyObservers(title);
    }

    public void setUID(String UID) {

        this.UID = UID;
        this.setChanged();
        this.notifyObservers(UID);
    }

    public void setApartment1String(String apartment1String) {
        this.apartment1String = apartment1String;
        this.setChanged();
        this.notifyObservers(apartment1String);

    }

    public void setApartment2String(String apartment2String) {
        this.apartment2String = apartment2String;
        this.setChanged();
        this.notifyObservers(apartment2String);
    }

    public void setApartment3String(String apartment3String) {
        this.apartment3String = apartment3String;
        this.setChanged();
        this.notifyObservers(apartment3String);
    }

    public void setPrice_per_night(int price_per_night) {

        this.price_per_night = price_per_night;
        this.setChanged();
        this.notifyObservers(price_per_night);
    }

    public void setNumRatings(int numRatings) {

        this.numRatings = numRatings;
        this.setChanged();
        this.notifyObservers(numRatings);
    }

    public void setIsTwoPeopleAllowed(int isTwoPeopleAllowed) {
        this.isTwoPeopleAllowed = isTwoPeopleAllowed;
        this.setChanged();
        this.notifyObservers(isTwoPeopleAllowed);
    }

    public void setRatingAverage(double ratingAverage) {

        this.ratingAverage = ratingAverage;
        this.setChanged();
        this.notifyObservers(ratingAverage);
    }

    public void setAID(String AID) {
        this.AID = AID;
        this.setChanged();
        this.notifyObservers(AID);
    }

    public int getNumSubleasedNightsLeft() {
        return numSubleasedNightsLeft;
    }

    public String getHostUID() {
        return hostUID;
    }

    public String getCity_or_village() {
        return city_or_village;
    }

    public String getDescription() {
        return description;
    }

    public String getHouse_number() {
        return house_number;
    }

    public String getLocationID() {
        return locationID;
    }

    public String getStreet() {
        return street;
    }

    public String getTitle() {
        return title;
    }

    public String getUID() {
        return UID;
    }

    public String getApartment1String() {
        return apartment1String;
    }

    public String getApartment2String() {
        return apartment2String;
    }

    public String getApartment3String() {
        return apartment3String;
    }

    public int getPrice_per_night() {
        return price_per_night;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public int getIsTwoPeopleAllowed() {
        return isTwoPeopleAllowed;
    }

    public double getRatingAverage() {
        return ratingAverage;
    }

    public String getIBAN() {
        return IBAN;
    }


    public String getAID() {
        return AID;
    }
}
