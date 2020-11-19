package com.clj.blesample.model;

import java.util.Date;

public class GasConsumptionPatternDTO {

    private int id;
    private String gasUsageBurner;
    private int gasUsage;
    private Date gasUsageDate;

    public GasConsumptionPatternDTO(int id, String gasUsageBurner, int gasUsage, Date gasUsageDate) {
        this.id = id;
        this.gasUsageBurner = gasUsageBurner;
        this.gasUsage = gasUsage;
        this.gasUsageDate = gasUsageDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGasUsageBurner() {
        return gasUsageBurner;
    }

    public void setGasUsageBurner(String gasUsageBurner) {
        this.gasUsageBurner = gasUsageBurner;
    }

    public int getGasUsage() {
        return gasUsage;
    }

    public void setGasUsage(int gasUsage) {
        this.gasUsage = gasUsage;
    }

    public Date getGasUsageDate() {
        return gasUsageDate;
    }

    public void setGasUsageDate(Date gasUsageDate) {
        this.gasUsageDate = gasUsageDate;
    }
}
