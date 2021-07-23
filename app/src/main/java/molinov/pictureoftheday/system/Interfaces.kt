package molinov.pictureoftheday.system

import androidx.recyclerview.widget.RecyclerView
import molinov.pictureoftheday.picture.Data

interface OnListItemClickListener {
    fun onItemClick(data: Data)
}

interface OnStartDragListener {
    fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
}

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int)
    fun onItemDismiss(position: Int)
}

interface ItemTouchHelperViewHolder {
    fun onItemSelected()
    fun onItemClear()
}
