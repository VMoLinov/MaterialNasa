package molinov.pictureoftheday.system

import molinov.pictureoftheday.picture.Data

interface OnListItemClickListener {
    fun onItemClick(data: Data)
}

interface OnStartDragListener {
    fun onStartDrag(viewHolder: SystemRecyclerAdapter.BaseViewHolder)
}

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int)
    fun onItemDismiss(position: Int)
}

interface ItemTouchHelperViewHolder {
    fun onItemSelected()
    fun onItemClear()
}
