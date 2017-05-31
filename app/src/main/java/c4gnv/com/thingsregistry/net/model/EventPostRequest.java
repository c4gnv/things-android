package c4gnv.com.thingsregistry.net.model;

import java.io.Serializable;

public class EventPostRequest implements Serializable {

    private static final long serialVersionUID = 1;

    private String serialNumber;
    private String clickType;
    private int rssiDb;
    private int batteryVoltage;

    public EventPostRequest() {
        // No-op
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getBatteryVoltage() {
        return batteryVoltage;
    }

    public void setBatteryVoltage(int batteryVoltage) {
        this.batteryVoltage = batteryVoltage;
    }

    public String getClickType() {
        return clickType;
    }

    public void setClickType(String clickType) {
        this.clickType = clickType;
    }

    public int getRssiDb() {
        return rssiDb;
    }

    public void setRssiDb(int rssiDb) {
        this.rssiDb = rssiDb;
    }
}
