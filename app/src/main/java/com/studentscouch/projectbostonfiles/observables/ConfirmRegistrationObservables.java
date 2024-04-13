package com.studentscouch.projectbostonfiles.observables;

import android.widget.ImageView;

import java.util.List;
import java.util.Observable;

public class ConfirmRegistrationObservables extends Observable {

    private String hostStatus;
    private String profile_image;
    private String apartementImage1;
    private String gender;
    private String locationID;
    private String firstName;
    private String lastName;
    private String email;
    private String birthdate;
    private String place_of_residence;
    private String cityVillage;
    private String street;
    private String house_number;
    private String title;
    private String description;
    private String price_per_night;
    private String profile_picture;
    private int year;
    private int month;
    private int day;
    private String countryCode;
    private String AID;

    private int is_two_people_allowed;
    private int subleasedNightsLeft;

    private List<ImageView> apartmentImages;

    public int getSubleasedNightsLeft() {
        return subleasedNightsLeft;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getAID() {
        return AID;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public String getHostStatus() {
        return hostStatus;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public String getApartementImage1() {
        return apartementImage1;
    }

    public String getGender() {
        return gender;
    }

    public String getLocationID() {
        return locationID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getStreet() {
        return street;
    }

    public String getHouse_number() {
        return house_number;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice_per_night() {
        return price_per_night;
    }

    public int getIs_two_people_allowed() {
        return is_two_people_allowed;
    }

    public String getCityVillage() {
        return cityVillage;
    }

    public String getPlace_of_residence() {
        return place_of_residence;
    }

    public List<ImageView> getApartmentImages() {
        return apartmentImages;
    }

    public void setSubleasedNightsLeft(int subleasedNightsLeft) {
        this.subleasedNightsLeft = subleasedNightsLeft;
        this.setChanged();
        this.notifyObservers(subleasedNightsLeft);
    }

    public void setYear(int year) {
        this.year = year;
        this.setChanged();
        this.notifyObservers(year);
    }

    public void setMonth(int month) {
        this.month = month;
        this.setChanged();
        this.notifyObservers(month);
    }

    public void setDay(int day) {
        this.day = day;
        this.setChanged();
        this.notifyObservers(day);
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        this.setChanged();
        this.notifyObservers(countryCode);
    }

    public void setAID(String AID) {
        this.AID = AID;
        this.setChanged();
        this.notifyObservers(AID);
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
        this.setChanged();
        this.notifyObservers(profile_picture);
    }

    public void setHostStatus(String hostStatus) {
        this.hostStatus = hostStatus;
        this.setChanged();
        this.notifyObservers(hostStatus);
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
        this.setChanged();
        this.notifyObservers(profile_image);
    }

    public void setApartementImage1(String apartementImage1) {
        this.apartementImage1 = apartementImage1;
        this.setChanged();
        this.notifyObservers(apartementImage1);
    }

    public void setGender(String gender) {
        this.gender = gender;
        this.setChanged();
        this.notifyObservers(gender);
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
        this.setChanged();
        this.notifyObservers(locationID);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        this.setChanged();
        this.notifyObservers(firstName);
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        this.setChanged();
        this.notifyObservers(lastName);
    }

    public void setEmail(String email) {
        this.email = email;
        this.setChanged();
        this.notifyObservers(email);
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
        this.setChanged();
        this.notifyObservers(birthdate);
    }

    public void setPlace_of_residence(String place_of_residence) {
        this.place_of_residence = place_of_residence;
        this.setChanged();
        this.notifyObservers(place_of_residence);
    }

    public void setCityVillage(String cityVillage) {
        this.cityVillage = cityVillage;
        this.setChanged();
        this.notifyObservers(cityVillage);
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

    public void setTitle(String title) {
        this.title = title;
        this.setChanged();
        this.notifyObservers(title);
    }

    public void setDescription(String description) {
        this.description = description;
        this.setChanged();
        this.notifyObservers(description);
    }

    public void setPrice_per_night(String price_per_night) {
        this.price_per_night = price_per_night;
        this.setChanged();
        this.notifyObservers(price_per_night);
    }

    public void setIs_two_people_allowed(int is_two_people_allowed) {
        this.is_two_people_allowed = is_two_people_allowed;
        this.setChanged();
        this.notifyObservers(is_two_people_allowed);
    }

    public void setApartmentImages(List<ImageView> apartmentImages) {
        this.apartmentImages = apartmentImages;
        this.setChanged();
        this.notifyObservers(apartmentImages);
    }
}
