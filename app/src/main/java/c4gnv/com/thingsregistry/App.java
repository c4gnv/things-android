package c4gnv.com.thingsregistry;

import android.app.Application;

import java.io.IOException;

import c4gnv.com.thingsregistry.net.ServiceApi;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class App extends Application {

    private static App instance;
    private ServiceApi serviceApi;

    public static App get() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public ServiceApi getServiceApi() {
        if (serviceApi == null) {
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Interceptor.Chain chain) throws IOException {
                            Request.Builder requestBuilder = chain.request().newBuilder();
                            return chain.proceed(requestBuilder.build());
                        }
                    })
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ServiceApi.BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();

            serviceApi = retrofit.create(ServiceApi.class);
        }
        return serviceApi;
    }
}
