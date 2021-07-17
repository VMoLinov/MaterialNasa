package molinov.pictureoftheday.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import molinov.pictureoftheday.R
import molinov.pictureoftheday.picture.PictureOfTheDayData
import molinov.pictureoftheday.picture.PictureOfTheDayViewModel

class ViewPagerItems : Fragment() {

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
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
                        findViewById<TextView>(R.id.title_view).text = serverResponseData.title
                        findViewById<TextView>(R.id.explanation_view).text =
                            serverResponseData.explanation
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
