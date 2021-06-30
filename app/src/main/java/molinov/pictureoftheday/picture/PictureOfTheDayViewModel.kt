package molinov.pictureoftheday.picture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import molinov.pictureoftheday.BuildConfig
import molinov.pictureoftheday.util.BEFORE_YESTERDAY
import molinov.pictureoftheday.util.TODAY
import molinov.pictureoftheday.util.YESTERDAY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

class PictureOfTheDayViewModel(
    private val liveDataForViewToObserve: MutableLiveData<PictureOfTheDayData> = MutableLiveData(),
    private val retrofitImpl: PODRetrofitImpl = PODRetrofitImpl()
) : ViewModel() {

    fun getData(inputDate: String): LiveData<PictureOfTheDayData> {
        sendServerRequest(inputDate)
        return liveDataForViewToObserve
    }

    private fun sendServerRequest(inputDate: String) {
        liveDataForViewToObserve.value = PictureOfTheDayData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.timeInMillis = System.currentTimeMillis()
        val date: String = when (inputDate) {
            YESTERDAY -> {
                calendar.add(Calendar.DAY_OF_MONTH, -1)
                formatter.format(calendar.time)
            }
            BEFORE_YESTERDAY -> {
                calendar.add(Calendar.DAY_OF_MONTH, -2)
                formatter.format(calendar.time)
            }
            else -> formatter.format(calendar.time)
        }
        if (apiKey.isBlank()) {
            PictureOfTheDayData.Error(Throwable("You need API Key"))
        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(date, apiKey).enqueue(object :
                Callback<List<PODServerResponseData>> {
                override fun onResponse(
                    call: Call<List<PODServerResponseData>>,
                    response: Response<List<PODServerResponseData>>
                ) {
                    val responseTest: List<PODServerResponseData> = response.body()!!
                    if (response.isSuccessful && response.body() != null) {
                        liveDataForViewToObserve.value =
                            PictureOfTheDayData.Success(responseTest)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataForViewToObserve.value =
                                PictureOfTheDayData.Error(Throwable("Unidentified error"))
                        } else {
                            liveDataForViewToObserve.value =
                                PictureOfTheDayData.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<List<PODServerResponseData>>, t: Throwable) {
                    liveDataForViewToObserve.value = PictureOfTheDayData.Error(t)
                }
            })
        }
    }
}