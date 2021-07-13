package molinov.pictureoftheday.api

import molinov.pictureoftheday.picture.EarthServerResponseData
import molinov.pictureoftheday.picture.PODServerResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfTheDayAPI {

    @GET("planetary/apod")
    fun getPictureOfTheDay(
        @Query("date") date: String,
        @Query("api_key") apiKey: String
    ): Call<PODServerResponseData>

    @GET("EPIC/api/natural/images")
    fun getAvailablePictures(
        @Query("api_key") apiKey: String
    ): Call<List<EarthServerResponseData>>

//    @GET("EPIC/{path}")
//    fun getEarthPicture(
//        @Path("path") path: String
//    ):Call

    @GET("planetary/apod")
    fun getRandomPicture(
        @Query("count") count: Int,
        @Query("api_key") apiKey: String
    ): Call<List<PODServerResponseData>>
}
