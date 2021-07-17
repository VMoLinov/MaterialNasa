package molinov.pictureoftheday.earth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import molinov.pictureoftheday.BuildConfig
import molinov.pictureoftheday.picture.EarthData
import molinov.pictureoftheday.picture.EarthServerResponseData
import molinov.pictureoftheday.picture.PODRetrofitImpl
import molinov.pictureoftheday.picture.baseURL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EarthFragmentViewModel(
    private val liveDataForViewToObserve: MutableLiveData<EarthData> = MutableLiveData(),
    private val retrofitImpl: PODRetrofitImpl = PODRetrofitImpl()
) : ViewModel() {

    private val IMAGES = 3

    fun getData(): LiveData<EarthData> {
        sendServerRequest()
        return liveDataForViewToObserve
    }

    private fun sendServerRequest() {
        liveDataForViewToObserve.value = EarthData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        retrofitImpl.getEarthPictures().getAvailablePictures(apiKey).enqueue(object :
            Callback<List<EarthServerResponseData>> {

            override fun onResponse(
                call: Call<List<EarthServerResponseData>>,
                response: Response<List<EarthServerResponseData>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val url = buildURL(response.body()!!)
                    liveDataForViewToObserve.value = EarthData.Success(url)
                } else {
                    val message = response.message()
                    if (message.isNullOrEmpty()) {
                        liveDataForViewToObserve.value = EarthData.Error(Throwable("Error"))
                    } else liveDataForViewToObserve.value = EarthData.Error(Throwable(message))
                }
            }

            override fun onFailure(call: Call<List<EarthServerResponseData>>, t: Throwable) {
                liveDataForViewToObserve.value = EarthData.Error(t)
            }
        })
    }

    private fun buildURL(data: List<EarthServerResponseData>): Map<String, String> {
        val calendar = Calendar.getInstance()
        val urls: MutableMap<String, String> = HashMap()
        val count: MutableList<Int> = ArrayList()
        var index: Int
        for (i in 1..IMAGES) {
            do index = Random().nextInt(data.size)
            while (count.contains(index))
            count.add(index)
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).parse(data[index].date)
            calendar.time = formatter!!
            val dateToSrv = SimpleDateFormat("yyyy/MM/dd", Locale.US).format(calendar.time)
            val dateToReturn =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(calendar.time)
            urls[dateToReturn] =
                "${baseURL}EPIC/archive/natural/$dateToSrv/png/${data[index].image}.png?api_key=${BuildConfig.NASA_API_KEY}"
        }
        return urls
    }
}
