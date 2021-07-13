package molinov.pictureoftheday.earth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import molinov.pictureoftheday.BuildConfig
import molinov.pictureoftheday.databinding.FragmentEarthBinding
import molinov.pictureoftheday.picture.EarthData
import molinov.pictureoftheday.picture.baseURL
import java.text.SimpleDateFormat
import java.util.*

class EarthFragment : Fragment() {

    private var _binding: FragmentEarthBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EarthFragmentViewModel by lazy {
        ViewModelProvider(this).get(EarthFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEarthBinding.inflate(inflater, container, false)
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
                binding.imageView.load(url)
            }
            is EarthData.Loading -> {
            }
            is EarthData.Error -> {
            }
            else -> {
            }
        }
    }

    private fun buildURL(data: EarthData.Success): String {
        val response = data.serverResponseData
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(response[0].date)
        val calendar = Calendar.getInstance()
        calendar.time = formatter!!
        val format = SimpleDateFormat("yyyy/MM/dd", Locale.US)
        val date = format.format(calendar.time)
        return "${baseURL}EPIC/archive/natural/$date/png/${response[0].image}.png?api_key=${BuildConfig.NASA_API_KEY}"
    }

    companion object {
        fun newInstance() = EarthFragment()
    }
}
