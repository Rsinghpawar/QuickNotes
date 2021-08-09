package com.rscorp.quicknotes.util.genericAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class GenericAdapter<T, D>(context: Context, arrayList: ArrayList<T> = ArrayList()) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
  private val mContext: Context = context
  private var mArrayList: ArrayList<T> = arrayList

  abstract fun getLayoutResId(viewType: Int?): Int
  abstract fun onBindData(model: T, position: Int, dataBinding: D)
  abstract fun onItemClick(view: View, model: T, position: Int)
  open fun onItemClick(dataBinding: D, model: T, position: Int){}
  abstract fun onInnerItemClick(view: View, model: T, position: Int)
  abstract fun getInnerClickableItem(dataBinding: D): ArrayList<View>?
  open fun getItemType(position: Int): Int = 0

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    val dataBinding: ViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
      getLayoutResId(viewType), parent, false)
    return ItemViewHolder<D>(dataBinding)
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    if (!mArrayList.isNullOrEmpty()) {
      mArrayList[position]?.let {
        onBindData(it, position, (holder as ItemViewHolder<D>).mDataBinding)
      }
    }

    ((holder as ItemViewHolder<D>).mDataBinding as ViewDataBinding).root.setOnClickListener {

      mArrayList[position]?.let { item ->
        onItemClick(it, item, position)
        onItemClick((holder as ItemViewHolder<D>).mDataBinding, item, position)
      }

    }
    val innerClickableItemList = getInnerClickableItem((holder as ItemViewHolder<D>).mDataBinding)
    if (innerClickableItemList != null && !innerClickableItemList.isNullOrEmpty()) {
      for (view in innerClickableItemList) {
        view.setOnClickListener {
          mArrayList[position]?.let { item ->
            onInnerItemClick(it, item, position)
          }
        }
      }
    }
  }

  override fun getItemCount(): Int {
    return mArrayList.size
  }

  override fun getItemId(position: Int): Long = mArrayList[0]?.hashCode()?.toLong() ?: 0L

  fun getItem(position: Int): T? = mArrayList[position]

  fun getItemList() = mArrayList

  override fun getItemViewType(position: Int): Int {
    return getItemType(position)
  }

  fun updateList(newList: List<T>) {
    mArrayList.clear()
    mArrayList.addAll(newList)
    notifyDataSetChanged()
  }

  fun updateItem(item: T, position: Int) {
    mArrayList[position] = item
    notifyItemChanged(position)
  }

  fun updateListWithoutNotify(newList: List<T>) {
    val tempList = ArrayList<T>()
    tempList.addAll(mArrayList)
    mArrayList = ArrayList(newList)
    val diffResult = DiffUtil.calculateDiff(DiffUtilCallback(tempList, newList), true)
    diffResult.dispatchUpdatesTo(this)
  }

  fun removeItem(position: Int) {
    if (!mArrayList.isNullOrEmpty() && (mArrayList.size - 1) >= position) {
      mArrayList.removeAt(position)
      notifyItemRemoved(position)
      notifyItemRangeChanged(position, mArrayList.size)
    }
  }

  fun appendList(newList: List<T>) {
    val oldList = mArrayList
    val tempList: ArrayList<T>? = ArrayList()
    tempList?.addAll(oldList)
    tempList?.addAll(newList)
    mArrayList.addAll(newList)
    notifyItemRangeChanged(oldList.size ?: 0, tempList?.size ?: 0)
  }

  fun clearAdapter() {
    mArrayList.clear()
    notifyDataSetChanged()
  }

}