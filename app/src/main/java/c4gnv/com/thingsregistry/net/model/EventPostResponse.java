package c4gnv.com.thingsregistry.net.model;

import java.io.Serializable;

public class EventPostResponse implements Serializable {

    private static final long serialVersionUID = 1;

    private String count;

    public EventPostResponse() {
        // No-op
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
