package c4gnv.com.thingsregistry.net;

import c4gnv.com.thingsregistry.net.model.EventPostRequest;
import c4gnv.com.thingsregistry.net.model.EventPostResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ServiceApi {

    public static final String BASE_URL = "https://www.google.com";

    @POST()
    Call<EventPostResponse> postEvent(@Url String url, @Header("Authorization") String authorizationToken, @Body EventPostRequest eventPostRequest);
}
