package com.clj.blesample.model;

public class MaintenaceServiceDTO {

    String mServiceIssue_ID;
    String mServiceDate;
    String mServiceDName;
    String mServiceIssue;
    String mDeviceID;

    public MaintenaceServiceDTO(String mServiceIssue_ID, String mServiceDate, String mServiceDName, String mServiceIssue, String mDeviceID) {
        this.mServiceIssue_ID = mServiceIssue_ID;
        this.mServiceDate = mServiceDate;
        this.mServiceDName = mServiceDName;
        this.mServiceIssue = mServiceIssue;
        this.mDeviceID = mDeviceID;
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

    public String getmDeviceID() {
        return mDeviceID;
    }
}
