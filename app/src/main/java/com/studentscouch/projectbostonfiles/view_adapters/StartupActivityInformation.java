package com.studentscouch.projectbostonfiles.view_adapters;

public class StartupActivityInformation {

    private int
                image;
        private String
                name;

    public StartupActivityInformation(int iconId, String name) {

        this.image = iconId;
        this.name = name;

    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }
}
