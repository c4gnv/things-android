package c4gnv.com.thingsregistry.net.model;

import java.io.Serializable;

public class EventPostRequest implements Serializable {

    private static final long serialVersionUID = 1;

    private String serialNumber;
    private String batteryVoltage;
    private String clickType;
    private int rssiDb;

    public EventPostRequest() {
        // No-op
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getBatteryVoltage() {
        return batteryVoltage;
    }

    public void setBatteryVoltage(String batteryVoltage) {
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
