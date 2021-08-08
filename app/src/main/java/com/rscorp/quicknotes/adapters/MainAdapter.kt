package com.rscorp.quicknotes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rscorp.quicknotes.R
import com.rscorp.quicknotes.db.models.CurrentNoteData

class MainAdapter : RecyclerView.Adapter<MainAdapter.MyViewHolder>() {

    private var list: List<CurrentNoteData> = emptyList()
    fun setListData(list: List<CurrentNoteData>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(position: Int) {
            val tv_title = itemView.findViewById<TextView>(R.id.tv_title)
            val tv_desc = itemView.findViewById<TextView>(R.id.tv_desc)
            tv_title.text = list[position].title
            tv_desc.text = list[position]?.description
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_rv , parent , false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItems(position)
    }

    override fun getItemCount(): Int  = list.size
}