package c4gnv.com.thingsregistry.net;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ServiceCallback<T> implements Callback<T> {

    private int statusCode;

    public abstract void onSuccess(T response);

    public abstract void onFail(ServiceError error);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        this.statusCode = response.code();

        if (response.isSuccessful()) {
            onSuccess(response.body());
        } else {
            try {
                String errorJson = response.errorBody().string();
                handleConnectionError(call, statusCode, errorJson);
            } catch (Exception e) {
                handleException(call, e);
            }
        }
    }

    private void handleConnectionError(Call<T> call, int responseCode, String errorJson) {
        onFail(ServiceError.createError(call.request(), ServiceError.ErrorType.CONNECTION));
    }

    private void handleException(Call<T> call, Exception e) {
        onFail(ServiceError.createError(call.request(), ServiceError.ErrorType.UNKNOWN));
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onFail(ServiceError.from(call, t));
    }
}
