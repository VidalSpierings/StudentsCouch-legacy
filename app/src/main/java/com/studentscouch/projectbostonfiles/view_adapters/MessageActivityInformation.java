package com.studentscouch.projectbostonfiles.view_adapters;

public class MessageActivityInformation {

    private int
        profileImage,
        notificationImage;

    public MessageActivityInformation(int profileImage, int notificationImage) {
        this.profileImage = profileImage;
        this.notificationImage = notificationImage;
    }

    int getProfileImage() {
        return profileImage;
    }

    int getNotificationImage() {
        return notificationImage;
    }
}
