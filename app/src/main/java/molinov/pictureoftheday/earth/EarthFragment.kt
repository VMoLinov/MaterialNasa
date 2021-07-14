package molinov.pictureoftheday.earth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import molinov.pictureoftheday.BuildConfig
import molinov.pictureoftheday.databinding.FragmentEarthEndBinding
import molinov.pictureoftheday.databinding.FragmentEarthStartBinding
import molinov.pictureoftheday.picture.EarthData
import molinov.pictureoftheday.picture.baseURL
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EarthFragment : Fragment() {

    private var _binding: FragmentEarthEndBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EarthFragmentViewModel by lazy {
        ViewModelProvider(this).get(EarthFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEarthEndBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData().observe(viewLifecycleOwner, { renderData(it) })
    }

    private fun renderData(data: EarthData) {
        when (data) {
            is EarthData.Success -> {
                val url = buildURL(data)
                val response = data.serverResponseData
                binding.apply {
                    val images = listOf<ImageView>(imageViewTop, imageViewCenter, imageViewBottom)
                    val texts = listOf(textViewTop, textViewCenter, textViewBottom)
                    var index = 0
                    for (item in url) {
                        images[index].load(item.value)
                        texts[index++].text = response[item.key].date
                    }
                    buttonEarth.visibility = View.VISIBLE
                }
            }
            is EarthData.Loading -> {
            }
            is EarthData.Error -> {
            }
            else -> {
            }
        }
    }

    private fun buildURL(data: EarthData.Success): Map<Int, String> {
        val response = data.serverResponseData
        val calendar = Calendar.getInstance()
        val urls: MutableMap<Int, String> = HashMap()
        val count: MutableList<Int> = ArrayList()
        var index: Int
        for (i in 1..IMAGES) {
            do index = Random().nextInt(response.size)
            while (count.contains(index))
            count.add(index)
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(response[index].date)
            calendar.time = formatter!!
            val date = SimpleDateFormat("yyyy/MM/dd", Locale.US).format(calendar.time)
            urls[index] =
                "${baseURL}EPIC/archive/natural/$date/png/${response[index].image}.png?api_key=${BuildConfig.NASA_API_KEY}"
        }
        return urls
    }

    companion object {
        const val IMAGES = 3
        fun newInstance() = EarthFragment()
    }
}
