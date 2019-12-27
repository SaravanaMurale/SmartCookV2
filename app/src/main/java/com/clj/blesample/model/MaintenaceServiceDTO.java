package com.clj.blesample.model;

public class MaintenaceServiceDTO {

    String mServiceIssue_ID;
    String mServiceDate;
    String mServiceDName;
    String mServiceIssue;

    public MaintenaceServiceDTO(String mServiceIssue_ID, String mServiceDate, String mServiceDName, String mServiceIssue) {
        this.mServiceIssue_ID = mServiceIssue_ID;
        this.mServiceDate = mServiceDate;
        this.mServiceDName = mServiceDName;
        this.mServiceIssue = mServiceIssue;
    }

    public String getmServiceIssue_ID() {
        return mServiceIssue_ID;
    }

    public String getmServiceDate() {
        return mServiceDate;
    }

    public String getmServiceDName() {
        return mServiceDName;
    }

    public String getmServiceIssue() {
        return mServiceIssue;
    }
}
