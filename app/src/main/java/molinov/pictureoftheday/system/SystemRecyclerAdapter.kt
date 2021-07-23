package molinov.pictureoftheday.system

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import molinov.pictureoftheday.R
import molinov.pictureoftheday.picture.Data

class SystemRecyclerAdapter(
    private var onListItemClickListener: OnListItemClickListener,
    private var data: MutableList<Pair<Data, Boolean>>
) : RecyclerView.Adapter<SystemRecyclerAdapter.BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_EARTH -> {
                EarthViewHolder(
                    inflater.inflate(
                        R.layout.fragment_recycler_item_earth, parent,
                        false
                    ) as View
                )
            }
            TYPE_MARS -> {
                MarsViewHolder(
                    inflater.inflate(
                        R.layout.fragment_recycler_item_mars, parent,
                        false
                    ) as View
                )
            }
            else -> {
                HeaderViewHolder(
                    inflater.inflate(
                        R.layout.fragment_recycle_item_header, parent,
                        false
                    ) as View
                )
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> TYPE_HEADER
            data[position].first.someDescription.isNullOrBlank() -> TYPE_MARS
            else -> TYPE_EARTH
        }
    }

    fun appendItem() {
        data.add(generateItem())
        notifyItemInserted(itemCount - 1)
    }

    private fun generateItem() = Pair(Data("Mars", ""), false)

    inner class EarthViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Pair<Data, Boolean>) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.findViewById<AppCompatTextView>(R.id.descriptionTextView).text =
                    data.first.someDescription
                itemView.findViewById<AppCompatImageView>(R.id.wikiImageView).setOnClickListener {
                    onListItemClickListener.onItemClick(data.first)
                }
            }
        }
    }

    inner class MarsViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Pair<Data, Boolean>) {
            itemView.findViewById<AppCompatImageView>(R.id.marsImageView).setOnClickListener {
                onListItemClickListener.onItemClick(data.first)
            }
            itemView.findViewById<AppCompatImageView>(R.id.addItemImageView).setOnClickListener {
                addItem()
            }
            itemView.findViewById<AppCompatImageView>(R.id.removeItemImageView).setOnClickListener {
                removeItem()
            }
            itemView.findViewById<AppCompatImageView>(R.id.moveItemUp).setOnClickListener {
                moveUp()
            }
            itemView.findViewById<AppCompatImageView>(R.id.moveItemDown).setOnClickListener {
                moveDown()
            }
            itemView.findViewById<AppCompatTextView>(R.id.marsDescriptionTextView).visibility =
                if (data.second) View.VISIBLE else View.GONE
            itemView.findViewById<AppCompatTextView>(R.id.marsTextView).setOnClickListener {
                toggleText()
            }
        }

        private fun toggleText() {
            data[layoutPosition] = data[layoutPosition].let {
                it.first to !it.second
            }
            notifyItemChanged(layoutPosition)
        }

        private fun addItem() {
            data.add(layoutPosition, generateItem())
            notifyItemInserted(layoutPosition)
        }

        private fun removeItem() {
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        private fun moveUp() {
            layoutPosition.takeIf { it > 1 }?.also { currentPosition ->
                data.removeAt(currentPosition).apply {
                    data.add(currentPosition - 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition - 1)
            }
        }

        private fun moveDown() {
            layoutPosition.takeIf { it > 1 }?.also { currentPosition ->
                data.removeAt(currentPosition).apply {
                    data.add(currentPosition + 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition + 1)
            }
        }
    }

    inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Pair<Data, Boolean>) {
            itemView.findViewById<AppCompatTextView>(R.id.header).setOnClickListener {
                onListItemClickListener.onItemClick(data.first)
            }
        }
    }

    interface OnListItemClickListener {
        fun onItemClick(data: Data)
    }

    companion object {
        private const val TYPE_EARTH = 0
        private const val TYPE_MARS = 1
        private const val TYPE_HEADER = 2
    }

    abstract class BaseViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        abstract fun bind(data: Pair<Data, Boolean>)
    }
}
