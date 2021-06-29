package molinov.pictureoftheday.picture

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import molinov.pictureoftheday.R
import molinov.pictureoftheday.databinding.MainFragmentBinding
import molinov.pictureoftheday.viewmodel.PictureOfTheDayViewModel

class PictureOfTheDayFragment : Fragment() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }
        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getData()
            .observe(this@PictureOfTheDayFragment, { renderData(it) })
    }

    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
//                    showError()
                } else {
//                    showSuccess()
                    binding.imageView.load(url) {
                        lifecycle(this@PictureOfTheDayFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                    }
                }
            }
            is PictureOfTheDayData.Loading -> {
//                showLoading()
            }
            is PictureOfTheDayData.Error -> {
//                showError()
            }
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_DRAGGING -> TODO()
                    BottomSheetBehavior.STATE_COLLAPSED -> TODO()
                    BottomSheetBehavior.STATE_EXPANDED -> TODO()
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> TODO()
                    BottomSheetBehavior.STATE_HIDDEN -> TODO()
                    BottomSheetBehavior.STATE_SETTLING -> TODO()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                TODO()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}