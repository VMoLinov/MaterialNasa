package molinov.pictureoftheday.picture

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import molinov.pictureoftheday.MainActivity
import molinov.pictureoftheday.R
import molinov.pictureoftheday.api.ApiActivity
import molinov.pictureoftheday.databinding.MainFragmentBinding
import molinov.pictureoftheday.settings.SettingsFragment
import molinov.pictureoftheday.util.BEFORE_YESTERDAY
import molinov.pictureoftheday.util.TODAY
import molinov.pictureoftheday.util.YESTERDAY

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
        setBottomAppBar(view)
        binding.chipGroup.findViewById<Chip>(R.id.today).isCheckable = true // ???
        viewModel.getData(TODAY).observe(viewLifecycleOwner, { renderData(it) })
        binding.chipGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.before_yesterday -> {
                    viewModel.getData(BEFORE_YESTERDAY)
                        .observe(viewLifecycleOwner, { renderData(it) })
                }
                R.id.yesterday -> {
                    viewModel.getData(YESTERDAY).observe(viewLifecycleOwner, { renderData(it) })
                }
                R.id.today -> {
                    viewModel.getData(TODAY).observe(viewLifecycleOwner, { renderData(it) })
                }
            }
        }
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
                    setBottomSheetData(
                        view?.findViewById(R.id.bottom_sheet_container),
                        serverResponseData
                    )
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

    private fun setBottomSheetData(
        bottomSheet: ConstraintLayout?, serverResponseData: PODServerResponseData
    ) {
        if (bottomSheet != null) {
            bottomSheet.findViewById<TextView>(R.id.bottom_sheet_description_header).text =
                serverResponseData.title
            bottomSheet.findViewById<TextView>(R.id.bottom_sheet_description).text =
                serverResponseData.explanation
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_SETTLING
//        bottomSheetBehavior.addBottomSheetCallback(object :
//            BottomSheetBehavior.BottomSheetCallback() {
//            override fun onStateChanged(bottomSheet: View, newState: Int) {
//                when (newState) {
//                    BottomSheetBehavior.STATE_DRAGGING -> TODO()
//                    BottomSheetBehavior.STATE_COLLAPSED -> TODO()
//                    BottomSheetBehavior.STATE_EXPANDED -> TODO()
//                    BottomSheetBehavior.STATE_HALF_EXPANDED -> TODO()
//                    BottomSheetBehavior.STATE_HIDDEN -> TODO()
//                    BottomSheetBehavior.STATE_SETTLING -> TODO()
//                }
//            }

//            override fun onSlide(bottomSheet: View, slideOffset: Float) {
//                TODO()
//            }
//        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> Toast.makeText(context, "Favorite", Toast.LENGTH_SHORT).show()
            R.id.app_bar_settings -> {
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, SettingsFragment.newInstance())
                    .addToBackStack(null)
                    .commit()
            }
            R.id.app_bar_navigation -> {
                val intent = Intent(context, ApiActivity::class.java)
                startActivity(intent)
            }
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment.newInstance()
                        .show(parentFragmentManager, "tag")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBottomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)
        binding.fab.setOnClickListener {
            if (isMain) {
                isMain = false
                binding.apply {
                    bottomAppBar.navigationIcon = null
                    bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                    fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_back_fab))
                    bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
                }
            } else {
                isMain = true
                binding.bottomAppBar.navigationIcon =
                    ContextCompat.getDrawable(context, R.drawable.ic_hamburger_menu_bottom_bar)
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_plus_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private var isMain = true
        fun newInstance() = PictureOfTheDayFragment()
    }
}
