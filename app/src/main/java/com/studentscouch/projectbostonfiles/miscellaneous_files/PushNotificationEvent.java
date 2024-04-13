package com.studentscouch.projectbostonfiles.miscellaneous_files;

public class PushNotificationEvent {

    private String title;
    private String message;
    private String personName;
    private String uid;
    private String fcmToken;

    public PushNotificationEvent(String title, String message, String personName, String uid, String fcmToken){

        this.title = title;
        this.message = message;
        this.personName = personName;
        this.uid = uid;
        this.fcmToken = fcmToken;

    }

    public String getTitle(){

        return title;

    }

    public void setTitle(String title) {

        this.title = title;

    }

    public String getMessage(){

        return message;

    }

    public void setMessage(String message) {

        this.message = message;

    }

    public String getPersonName(){

        return personName;

    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

}
