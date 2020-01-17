package com.clj.blesample.model;

public class StatisticsDTO {

    String cookingDate;
    String cookingID;
    int burnerNo;
    int burnerPosition;
    String cookingStartStatus;
    String cookingEndStatus;
    String cookingStartTime;
    String cookingEndTime;
    String totalCookingDuration;

    public StatisticsDTO() {
    }

    public StatisticsDTO(String cookingDate, String cookingID, int burnerNo, int burnerPosition, String cookingStartStatus, String cookingEndStatus, String cookingStartTime, String cookingEndTime, String totalCookingDuration) {
        this.cookingDate=cookingDate;
        this.cookingID = cookingID;
        this.burnerNo = burnerNo;
        this.burnerPosition = burnerPosition;
        this.cookingStartStatus = cookingStartStatus;
        this.cookingEndStatus = cookingEndStatus;
        this.cookingStartTime = cookingStartTime;
        this.cookingEndTime = cookingEndTime;
        this.totalCookingDuration = totalCookingDuration;
    }

    public StatisticsDTO(String cookingID) {
        this.cookingID = cookingID;
    }

    public StatisticsDTO(String cookingDate, String cookingStartTime, String cookingEndTime, String totalCookingDuration) {
        this.cookingDate = cookingDate;
        this.cookingStartTime = cookingStartTime;
        this.cookingEndTime = cookingEndTime;
        this.totalCookingDuration = totalCookingDuration;
    }

    public String getCookingDate() {
        return cookingDate;
    }

    public String getCookingID() {
        return cookingID;
    }

    public int getBurnerNo() {
        return burnerNo;
    }

    public int getBurnerPosition() {
        return burnerPosition;
    }

    public String getCookingStartStatus() {
        return cookingStartStatus;
    }

    public String getCookingEndStatus() {
        return cookingEndStatus;
    }

    public String getCookingStartTime() {
        return cookingStartTime;
    }

    public String getCookingEndTime() {
        return cookingEndTime;
    }

    public String getTotalCookingDuration() {
        return totalCookingDuration;
    }
}
