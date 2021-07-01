package molinov.pictureoftheday.picture

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfTheDayAPI {

    @GET("planetary/apod")
    fun getPictureOfTheDay(
        @Query("date") date: String,
        @Query("api_key") apiKey: String
    ): Call<PODServerResponseData>

    @GET("planetary/apod")
    fun getRandomPicture(
        @Query("count") count: Int,
        @Query("api_key") apiKey: String
    ): Call<List<PODServerResponseData>>
}
