package c4gnv.com.thingsregistry.net;

import java.io.IOException;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.UnknownHostException;

import okhttp3.Request;
import retrofit2.Call;

public class ServiceError implements Serializable {

    private static final long serialVersionUID = 1;
    private int statusCode;
    private String error;
    private String message;
    public ServiceError(int statusCode, String error, String description) {
        this.statusCode = statusCode;
        this.error = error;
        this.message = description;
    }

    public static ServiceError from(Call call, Throwable t) {

        Request request = call != null ? call.request() : null;

        if (t instanceof UnknownHostException || t instanceof ConnectException) {
            return createError(request, ErrorType.CONNECTION);
        }

        if (t instanceof IOException) {
            return createError(request, ErrorType.IOEXCEPTION);
        }

        return createError(request, ErrorType.UNKNOWN);
    }

    public static ServiceError createError(Request request, ErrorType type) {

        int statusCode;
        String error;
        String message;

        switch (type) {
            case IOEXCEPTION:
                statusCode = -2;
                error = "IOException";
                message = "Sorry, there was an IO exception.";
                break;
            case CONNECTION:
                statusCode = -3;
                error = "ConnectionError";
                message = "Sorry, there was a connection error.";
                break;
            case UNKNOWN:
            default:
                statusCode = -1;
                error = "UnknownError";
                message = "Sorry, something doesn't seem right.";
                break;
        }

        return new ServiceError(statusCode, error, message);
    }

    public enum ErrorType {
        UNKNOWN,
        IOEXCEPTION,
        CONNECTION
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
