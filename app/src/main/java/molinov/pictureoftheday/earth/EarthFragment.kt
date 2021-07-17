package molinov.pictureoftheday.earth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import molinov.pictureoftheday.BuildConfig
import molinov.pictureoftheday.databinding.FragmentEarthStartBinding
import molinov.pictureoftheday.picture.EarthData

class EarthFragment : Fragment() {

    private var _binding: FragmentEarthStartBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EarthFragmentViewModel by lazy {
        ViewModelProvider(this).get(EarthFragmentViewModel::class.java)
    }
    private lateinit var images: List<AppCompatImageView>
    private lateinit var texts: List<AppCompatTextView>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEarthStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            images = listOf(imageViewTop, imageViewCenter, imageViewBottom)
            texts = listOf(textViewTop, textViewCenter, textViewBottom)
        }
        viewModel.getData().observe(viewLifecycleOwner, { renderData(it) })
    }

    private fun renderData(data: EarthData) {
        when (data) {
            is EarthData.Success -> {
                var index = 0
                for ((key, value) in data.serverResponseData) {
                    texts[index].text = key
                    images[index++].load(value)
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

    companion object {
        fun newInstance() = EarthFragment()
    }
}