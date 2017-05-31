package c4gnv.com.thingsregistry.net

import c4gnv.com.thingsregistry.net.model.EventPostRequest
import c4gnv.com.thingsregistry.net.model.EventPostResponse
import c4gnv.com.thingsregistry.net.model.Piece
import c4gnv.com.thingsregistry.net.model.Thing
import c4gnv.com.thingsregistry.net.model.ThingType
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ServiceApi {

    @get:GET("/types")
    val types: Call<List<ThingType>>

    @GET("/things")
    fun getThingsByType(@Query("typeId") typeId: String): Call<List<Thing>>

    @GET("/piece/{pieceId}")
    fun getPieceById(@Path("pieceId") pieceId: String): Call<Piece>

    @POST
    fun postEvent(@Url url: String, @Header("Authorization") authorizationToken: String, @Body eventPostRequest: EventPostRequest): Call<EventPostResponse>

    companion object {
        val BASE_URL = "https://private-anon-87d71aa155-polls152.apiary-mock.com"
    }
}
