package c4gnv.com.thingsregistry.net.model;

public class EventPostRequest {

    private String serialNumber;
    private String batteryVoltage;
    private String clickType;

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
}
