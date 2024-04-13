package com.studentscouch.projectbostonfiles.observables;

import java.util.ArrayList;
import java.util.Observable;

public class BookingActivityObservables extends Observable {

    private String AID;

    private String
            city_or_village,
            street,
            house_number;

    private String
            firstNameHost,
            lastNameHost;

    private ArrayList<ArrayList<Integer>> bookedDatesList;

    private int isTwoPeopleAllowed;

    public void setIsTwoPeopleAllowed(int isTwoPeopleAllowed) {
        this.isTwoPeopleAllowed = isTwoPeopleAllowed;
        this.setChanged();
        this.notifyObservers(isTwoPeopleAllowed);
    }

    public void setBookedDatesList(ArrayList<ArrayList<Integer>> bookedDatesList) {
        this.bookedDatesList = bookedDatesList;
        this.setChanged();
        this.notifyObservers(bookedDatesList);
    }

    public void setAID(String AID) {
        this.AID = AID;
        this.setChanged();
        this.notifyObservers(AID);
    }

    public void setCity_or_village(String city_or_village) {
        this.city_or_village = city_or_village;
        this.setChanged();
        this.notifyObservers(city_or_village);
    }

    public void setStreet(String street) {
        this.street = street;
        this.setChanged();
        this.notifyObservers(street);
    }

    public void setHouse_number(String house_number) {
        this.house_number = house_number;
        this.setChanged();
        this.notifyObservers(house_number);
    }

    public void setFirstNameHost(String firstNameHost) {
        this.firstNameHost = firstNameHost;
        this.setChanged();
        this.notifyObservers(firstNameHost);
    }

    public void setLastNameHost(String lastNameHost) {
        this.lastNameHost = lastNameHost;
        this.setChanged();
        this.notifyObservers(lastNameHost);
    }

    public String getCity_or_village() {
        return city_or_village;
    }

    public String getStreet() {
        return street;
    }

    public String getHouse_number() {
        return house_number;
    }

    public String getFirstNameHost() {
        return firstNameHost;
    }

    public String getLastNameHost() {
        return lastNameHost;
    }
    public int getIsTwoPeopleAllowed() {
        return isTwoPeopleAllowed;
    }

    public String getAID() {
        return AID;
    }

    public ArrayList<ArrayList<Integer>> getBookedDatesList() {
        return bookedDatesList;
    }
}
