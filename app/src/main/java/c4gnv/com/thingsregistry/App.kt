package c4gnv.com.thingsregistry

import android.app.Application
import c4gnv.com.thingsregistry.net.ServiceApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class App : Application() {
    private var serviceApi: ServiceApi? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun getServiceApi(): ServiceApi {
        if (serviceApi == null) {
            val httpClient = OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val requestBuilder = chain.request().newBuilder()
                        chain.proceed(requestBuilder.build())
                    }
                    .build()

            val retrofit = Retrofit.Builder()
                    .baseUrl(ServiceApi.BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build()

            serviceApi = retrofit.create(ServiceApi::class.java)
        }
        return serviceApi!!
    }

    companion object {

        private var instance: App? = null

        fun get(): App {
            return instance!!
        }
    }
}
