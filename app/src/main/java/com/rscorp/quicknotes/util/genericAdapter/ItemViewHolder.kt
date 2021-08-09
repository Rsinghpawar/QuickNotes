package com.rscorp.quicknotes.util.genericAdapter
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class ItemViewHolder<D>(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
  var mDataBinding: D = binding as D
}