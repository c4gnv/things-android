package c4gnv.com.thingsregistry.net.model;

import java.util.List;

public class Thing {

    private String id;
    private String serialNumber;
    private String name;
    private String description;
    private String icon;
    private String typeId;
    private List<Integer> pieceId;

    public Thing() {
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

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public List<Integer> getPieceId() {
        return pieceId;
    }

    public void setPieceId(List<Integer> pieceId) {
        this.pieceId = pieceId;
    }
}
