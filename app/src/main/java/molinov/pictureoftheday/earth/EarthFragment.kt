package molinov.pictureoftheday.earth

import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import molinov.pictureoftheday.R
import molinov.pictureoftheday.databinding.FragmentEarthEndBinding
import molinov.pictureoftheday.picture.EarthData

class EarthFragment : Fragment() {

    private var _binding: FragmentEarthEndBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EarthFragmentViewModel by lazy {
        ViewModelProvider(this).get(EarthFragmentViewModel::class.java)
    }
    private var isExpanded = false
    private lateinit var images: List<AppCompatImageView>
    private lateinit var texts: List<AppCompatTextView>
    val set = ConstraintSet()

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
        binding.apply {
            images = listOf(imageViewTop, imageViewCenter, imageViewBottom)
            texts = listOf(textViewTop, textViewCenter, textViewBottom)
            viewModel.getData().observe(viewLifecycleOwner, { renderData(it) })
            imageViewTop.setOnClickListener {
                set.clone(binding.motion)
                changeConstraints(R.id.image_view_top)
                TransitionManager.beginDelayedTransition(binding.motion)
                set.applyTo(binding.motion)
            }
        }
    }

    private fun changeConstraints(viewId: Int) {
        set.connect(viewId, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0)
        set.connect(viewId, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0)
        set.connect(viewId, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0)
        set.connect(viewId, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0)

    }

    private fun renderData(data: EarthData) {
        when (data) {
            is EarthData.Success -> {
                var index = 0
                for ((key, value) in data.serverResponseData) {
                    texts[index].text = key
                    images[index++].load(value)
                }
                binding.motion.visibility = View.VISIBLE
                binding.loading.loadingLayout.visibility = View.GONE
            }
            is EarthData.Loading -> {
                binding.motion.visibility = View.GONE
                binding.loading.loadingLayout.visibility = View.VISIBLE
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