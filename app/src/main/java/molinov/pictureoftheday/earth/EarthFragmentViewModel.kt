package molinov.pictureoftheday.earth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import molinov.pictureoftheday.BuildConfig
import molinov.pictureoftheday.picture.EarthData
import molinov.pictureoftheday.picture.EarthServerResponseData
import molinov.pictureoftheday.picture.PODRetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EarthFragmentViewModel(
    private val liveDataForViewToObserve: MutableLiveData<EarthData> = MutableLiveData(),
    private val retrofitImpl: PODRetrofitImpl = PODRetrofitImpl()
) : ViewModel() {

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
                    liveDataForViewToObserve.value = EarthData.Success(response.body()!!)
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
}
