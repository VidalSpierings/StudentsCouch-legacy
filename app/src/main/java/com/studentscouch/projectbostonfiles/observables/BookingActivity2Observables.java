package com.studentscouch.projectbostonfiles.observables;

import java.util.ArrayList;
import java.util.Observable;

public class BookingActivity2Observables extends Observable {

    private int
            numDaysStay,
            yearInt,
            monthInt,
            dayInt,
            price_per_night,
            num_people;

    private String AID;

    private int numDaysLeftInteger;

    private ArrayList<ArrayList<Integer>> bookedDatesList;

    public void setYearInt(int yearInt) {
        this.yearInt = yearInt;
        this.setChanged();
        this.notifyObservers(yearInt);
    }

    public void setNumDaysStay(int numDaysStay) {
        this.numDaysStay = numDaysStay;
        this.setChanged();
        this.notifyObservers(numDaysStay);
    }

    public void setMonthInt(int monthInt) {
        this.monthInt = monthInt;
        this.setChanged();
        this.notifyObservers(monthInt);
    }

    public void setDayInt(int dayInt) {
        this.dayInt = dayInt;
        this.setChanged();
        this.notifyObservers(dayInt);
    }

    public void setAID(String AID) {
        this.AID = AID;
        this.setChanged();
        this.notifyObservers(AID);
    }

    public void setNumDaysLeftInteger(int numDaysLeftInteger) {
        this.numDaysLeftInteger = numDaysLeftInteger;
        this.setChanged();
        this.notifyObservers(numDaysLeftInteger);
    }

    public void setBookedDatesList(ArrayList<ArrayList<Integer>> bookedDatesList) {
        this.bookedDatesList = bookedDatesList;
        this.setChanged();
        this.notifyObservers(bookedDatesList);
    }

    public void setPrice_per_night(int price_per_night) {
        this.price_per_night = price_per_night;
        this.setChanged();
        this.notifyObservers(price_per_night);
    }

    public void setNum_people(int num_people) {
        this.num_people = num_people;
        this.setChanged();
        this.notifyObservers(num_people);
    }

    public int getMonthInt() {
        return monthInt;
    }

    public int getDayInt() {
        return dayInt;
    }

    public int getNumDaysStay() {
        return numDaysStay;
    }

    public int getYearInt() {
        return yearInt;
    }

    public String getAID() {
        return AID;
    }

    public int getNumDaysLeftInteger() {
        return numDaysLeftInteger;
    }

    public ArrayList<ArrayList<Integer>> getBookedDatesList() {
        return bookedDatesList;
    }

    public int getPrice_per_night() {
        return price_per_night;
    }

    public int getNum_people() {
        return num_people;
    }
}
