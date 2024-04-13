package com.studentscouch.projectbostonfiles.observables;

import java.util.Observable;

public class LogInActivityObservables extends Observable {

    private String month;
    private String hosStatus;
    private boolean isChecked;

    public String getMonth() {
        return month;
    }

    public String getHosStatus() {
        return hosStatus;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setMonth(String month) {
        this.month = month;
        this.setChanged();
        this.notifyObservers(month);
    }

    public void setHosStatus(String hosStatus) {
        this.hosStatus = hosStatus;
        this.setChanged();
        this.notifyObservers(hosStatus);
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
        this.setChanged();
        this.notifyObservers(checked);
    }
}
