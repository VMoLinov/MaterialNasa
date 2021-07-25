package molinov.pictureoftheday.viewpager

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.BackgroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.VideoView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import molinov.pictureoftheday.R
import molinov.pictureoftheday.picture.PictureOfTheDayData

class ViewPagerItems : Fragment() {

    private val viewModel: ViewPagerViewModel by lazy {
        ViewModelProvider(this).get(ViewPagerViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_pager_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arguments = requireArguments().getString(ARGS_KEY, ARGS_KEY)
        if (arguments != null) {
            viewModel.getData(arguments)
                .observe(viewLifecycleOwner, { renderData(it) })
        }
    }

    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
//                    TODO
                } else {
                    this.view?.apply {
                        val imageView = findViewById<ImageView>(R.id.image_view)
                        if (serverResponseData.mediaType != "image") {
                            val videoView = findViewById<VideoView>(R.id.video_view)
//                            videoView.setVideoURI(Uri.parse(url))
                            imageView.visibility = View.GONE
                            videoView.visibility = View.VISIBLE
//                            videoView.start()
                        }
                        imageView.load(url)
                        modifyTexts(
                            findViewById(R.id.title_view),
                            findViewById(R.id.explanation_view),
                            serverResponseData.title,
                            serverResponseData.explanation
                        )
                    }
                }
            }
            is PictureOfTheDayData.Loading -> {
            }
            is PictureOfTheDayData.Error -> {
            }
            else -> {
            }
        }
    }

    private fun modifyTexts(
        titleView: AppCompatTextView,
        explanationView: AppCompatTextView,
        titleResponse: String?,
        explanationResponse: String?
    ) {
//        val regex = Regex("(?i)(" + resources.getString(R.string.star) + "[\\s|s]|" + resources.getString(R.string.sun) + ")")
        val explanationList = splitToList(explanationResponse)
        val titleList = splitToList(titleResponse)
        titleView.text = setSpans(titleList)
        explanationView.text = setSpans(explanationList)
    }

    private fun splitToList(string: String?): List<String> {
        return string?.split(Regex(resources.getString(R.string.words_regex_modify))) ?: emptyList()
    }

    private fun setSpans(list: List<String>): SpannableStringBuilder {
        val spannable = SpannableStringBuilder()
        for (item in list) {
            spannable.append(item)
            if (item.contains(resources.getString(R.string.star_or_sun).toRegex()))
                spannable.setSpan(
                    BackgroundColorSpan(
                        resources.getColor(
                            (R.color.colorAccent),
                            requireContext().theme
                        )
                    ),
                    spannable.length - item.length,
                    spannable.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            if (item != list[list.size - 1]) spannable.append(" ")
        }
        return spannable
    }

    companion object {
        const val ARGS_KEY = "KEY"
        fun newInstance(DAY: String): Fragment {
            val bundle = Bundle()
            bundle.putString(ARGS_KEY, DAY)
            val fragment = ViewPagerItems()
            fragment.arguments = bundle
            return fragment
        }
    }
}
