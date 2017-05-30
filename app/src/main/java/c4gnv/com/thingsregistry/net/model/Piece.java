package c4gnv.com.thingsregistry.net.model;

import java.io.Serializable;
import java.util.UUID;

import c4gnv.com.thingsregistry.util.StringUtil;

public class Piece implements Serializable {

    private static final long serialVersionUID = 1;

    private String id;
    private String serialNumber;
    private String name;
    private String description;
    private String icon;
    private String url;
    private String token;
    private EventPostRequest normalEvent;
    private EventPostRequest diagnosticEvent;
    private EventPostRequest warningEvent;
    private EventPostRequest faultEvent;

    public Piece() {
        // No-op
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public EventPostRequest getNormalEvent() {
        return normalEvent;
    }

    public void setNormalEvent(EventPostRequest normalEvent) {
        this.normalEvent = normalEvent;
    }

    public EventPostRequest getDiagnosticEvent() {
        return diagnosticEvent;
    }

    public void setDiagnosticEvent(EventPostRequest diagnosticEvent) {
        this.diagnosticEvent = diagnosticEvent;
    }

    public EventPostRequest getWarningEvent() {
        return warningEvent;
    }

    public void setWarningEvent(EventPostRequest warningEvent) {
        this.warningEvent = warningEvent;
    }

    public EventPostRequest getFaultEvent() {
        return faultEvent;
    }

    public void setFaultEvent(EventPostRequest faultEvent) {
        this.faultEvent = faultEvent;
    }

    public void generateSerial() {
        if (StringUtil.isEmpty(serialNumber)) {
            this.serialNumber = UUID.randomUUID().toString();
        }
    }
}
