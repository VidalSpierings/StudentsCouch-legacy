package com.studentscouch.projectbostonfiles.observables;

import java.util.ArrayList;
import java.util.Observable;

public class StartupActivityObservables extends Observable {

    private ArrayList<String> unlockedCountriesArray;

    private String unlockedCountries;
    private String locationID;
    private String UID;
    private String UID2;
    private String AID;
    private String hostStatus;
    private String firstNameGuest;
    private String lastNameGuest;
    private String isUnlocked;
    private String firstNameHost;
    private String lastNameHost;
    private String placeID;
    private String country;
    private String city;

    private boolean isHost;
    private boolean hasBeenInitiated;
    private boolean isChanged;
    private boolean isCHangedAccepted2;
    private boolean isChangedAccepted3;
    private boolean isChanged4;
    private boolean isChanged5;
    private boolean isLoaded;

    private int endDay;
    private int endMonth;
    private int endYear;

    private String dummyString;

    public void setUnlockedCountriesArray(ArrayList<String> unlockedCountriesArray) {
        this.unlockedCountriesArray = unlockedCountriesArray;
        this.setChanged();
        this.notifyObservers(unlockedCountriesArray);
    }

    public void setUnlockedCountries(String unlockedCountries) {
        this.unlockedCountries = unlockedCountries;
        this.setChanged();
        this.notifyObservers(unlockedCountries);
    }

    public void setEndDay(int endDay) {
        this.endDay = endDay;
        this.setChanged();
        this.notifyObservers(endDay);
    }

    public void setEndMonth(int endMonth) {
        this.endMonth = endMonth;
        this.setChanged();
        this.notifyObservers(endMonth);
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
        this.setChanged();
        this.notifyObservers(endYear);
    }

    public void setUID(String UID) {
        this.UID = UID;
        this.setChanged();
        this.notifyObservers(UID);
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
        this.setChanged();
        this.notifyObservers(locationID);
    }

    public void setUID2(String UID2) {
        this.UID2 = UID2;
        this.setChanged();
        this.notifyObservers(UID2);
    }

    public void setAID(String AID) {
        this.AID = AID;
        this.setChanged();
        this.notifyObservers(AID);
    }

    public void setHostStatus(String hostStatus) {
        this.hostStatus = hostStatus;
        this.setChanged();
        this.notifyObservers(hostStatus);
    }

    public void setIsHost(boolean isHost) {
        this.isHost = isHost;
        this.setChanged();
        this.notifyObservers(isHost);
    }

    public void setHasBeenInitiated(boolean hasBeenInitiated) {
        this.hasBeenInitiated = hasBeenInitiated;
        this.setChanged();
        this.notifyObservers(hasBeenInitiated);
    }

    public void setChanged(boolean changed) {
        isChanged = changed;
        this.setChanged();
        this.notifyObservers(changed);
    }

    public void setCHangedAccepted2(boolean CHangedAccepted2) {
        isCHangedAccepted2 = CHangedAccepted2;
        this.setChanged();
        this.notifyObservers(CHangedAccepted2);
    }

    public void setChangedAccepted3(boolean changedAccepted3) {
        isChangedAccepted3 = changedAccepted3;
        this.setChanged();
        this.notifyObservers(changedAccepted3);
    }

    public void setFirstNameGuest(String firstNameGuest) {
        this.firstNameGuest = firstNameGuest;
        this.setChanged();
        this.notifyObservers(firstNameGuest);
    }

    public void setLastNameGuest(String lastNameGuest) {
        this.lastNameGuest = lastNameGuest;
        this.setChanged();
        this.notifyObservers(lastNameGuest);
    }

    public void setChanged4(boolean changed4) {
        isChanged4 = changed4;
        this.setChanged();
        this.notifyObservers(changed4);
    }

    public void setIsUnlocked(String isUnlocked) {
        this.isUnlocked = isUnlocked;
        this.setChanged();
        this.notifyObservers(isUnlocked);
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

    public void setLoaded(boolean loaded) {
        isLoaded = loaded;
        this.setChanged();
        this.notifyObservers(loaded);
    }

    public void setChanged5(boolean changed5) {
        isChanged5 = changed5;
        this.setChanged();
        this.notifyObservers(changed5);
    }

    public void setPlaceID(String placeID) {
        this.placeID = placeID;
        this.setChanged();
        this.notifyObservers(placeID);
    }

    public void setCity(String city) {
        this.city = city;
        this.setChanged();
        this.notifyObservers(city);
    }

    public void setCountry(String country) {
        this.country = country;
        this.setChanged();
        this.notifyObservers(country);
    }

    public String getUnlockedCountries() {
        return unlockedCountries;
    }

    public ArrayList<String> getUnlockedCountriesArray() {
        return unlockedCountriesArray;
    }

    public int getEndDay() {
        return endDay;
    }

    public int getEndMonth() {
        return endMonth;
    }

    public int getEndYear() {
        return endYear;
    }

    public String getLocationID() {
        return locationID;
    }

    public String getUID() {
        return UID;
    }

    public String getUID2() {
        return UID2;
    }

    public String getAID() {
        return AID;
    }

    public String getHostStatus() {
        return hostStatus;
    }

    public boolean getIsHost() {
        return isHost;
    }

    public boolean isHasBeenInitiated() {
        return hasBeenInitiated;
    }

    public boolean isChanged() {
        return isChanged;
    }

    public boolean isCHangedAccepted2() {
        return isCHangedAccepted2;
    }

    public boolean isChangedAccepted3() {
        return isChangedAccepted3;
    }

    public String getFirstNameGuest() {
        return firstNameGuest;
    }

    public String getLastNameGuest() {
        return lastNameGuest;
    }

    public boolean isChanged4() {
        return isChanged4;
    }

    public String getIsUnlocked() {
        return isUnlocked;
    }

    public String getFirstNameHost() {
        return firstNameHost;
    }

    public String getLastNameHost() {
        return lastNameHost;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public boolean isChanged5() {
        return isChanged5;
    }

    public String getPlaceID() {
        return placeID;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }




    public String getDummyString() {
        return dummyString;
    }

    public void setDummyString(String dummyString) {
        this.dummyString = dummyString;
        this.setChanged();
        this.notifyObservers(dummyString);
    }
}
