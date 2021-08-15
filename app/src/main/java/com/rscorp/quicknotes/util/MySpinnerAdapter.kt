package com.rscorp.quicknotes.util

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.marginBottom

class MySpinnerAdapter(context: Context, images: Array<Int>) :
    ArrayAdapter<Int>(context, android.R.layout.simple_spinner_item, images) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup) =
        getImageForPosition(position)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup) =
        getImageForPosition(position)

    private fun getImageForPosition(position: Int) = ImageView(context).apply {
        val params = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        params.setMargins(0,100,0,900)
        layoutParams = params
        setBackgroundResource(getItem(position)!!)

    }
}