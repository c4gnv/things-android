package c4gnv.com.thingsregistry.net;

import java.util.List;

import c4gnv.com.thingsregistry.net.model.EventPostRequest;
import c4gnv.com.thingsregistry.net.model.EventPostResponse;
import c4gnv.com.thingsregistry.net.model.Thing;
import c4gnv.com.thingsregistry.net.model.ThingType;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ServiceApi {

    public static final String BASE_URL = "https://private-anon-87d71aa155-polls152.apiary-mock.com";

    @GET("/types")
    Call<List<ThingType>> getTypes();

    @GET("/things?typeId=typeId")
    Call<List<Thing>> getThingsByType(@Field("typeId") String typeId);

    @POST()
    Call<EventPostResponse> postEvent(@Url String url, @Header("Authorization") String authorizationToken, @Body EventPostRequest eventPostRequest);
}
