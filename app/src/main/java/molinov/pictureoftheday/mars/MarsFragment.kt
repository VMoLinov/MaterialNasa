package molinov.pictureoftheday.mars

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.*
import molinov.pictureoftheday.R
import molinov.pictureoftheday.databinding.FragmentMarsExplodeBinding

class MarsFragment : Fragment() {

    private var _binding: FragmentMarsExplodeBinding? = null
    private val binding get() = _binding!!
    private var textIsVisible = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.button.setOnClickListener {
//            TransitionManager.beginDelayedTransition(
//                binding.transitionsContainer,
//                Slide(Gravity.END)
//            )
//            textIsVisible = !textIsVisible
//            binding.text.visibility = if (textIsVisible) View.VISIBLE else View.GONE
//        }
        binding.recyclerView.adapter = Adapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarsExplodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = MarsFragment()
    }

    private fun explode(clickedView: View) {
        val viewRect = Rect()
        clickedView.getGlobalVisibleRect(viewRect)
        val explode = Explode()
        explode.epicenterCallback = object : Transition.EpicenterCallback() {

            override fun onGetEpicenter(transition: Transition): Rect {
                return viewRect
            }
        }
        explode.excludeTarget(clickedView, true)
        val set = TransitionSet()
            .addTransition(explode)
            .addTransition(Fade().addTarget(clickedView))
            .addListener(object : TransitionListenerAdapter() {

                override fun onTransitionEnd(transition: Transition) {
                    transition.removeListener(this)
                }
            })
        TransitionManager.beginDelayedTransition(binding.recyclerView, set)
        binding.recyclerView.adapter = null
    }

    inner class Adapter : RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
                ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.fragment_mars_explode_recycler_item,
                    parent,
                    false
                ) as View
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.setOnClickListener {
                explode(it)
            }
        }

        override fun getItemCount(): Int {
            return 32
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
