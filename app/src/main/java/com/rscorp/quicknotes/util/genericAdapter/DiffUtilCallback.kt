package com.rscorp.quicknotes.util.genericAdapter

import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallback<T>(private val oldList: List<T>?, private val newList: List<T>): DiffUtil.Callback() {

    override fun getOldListSize(): Int  = oldList?.size ?: run { 0 }

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList?.let {
        it[oldItemPosition].hashCode() == newList[newItemPosition].hashCode()
    } ?: run {
        false
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =  oldList?.let { list ->
        list[oldItemPosition]?.let {
            it?.equals(newList[newItemPosition])
        } ?: run {
            false
        }
    } ?: run {
        false
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}