package com.example.hypekicks_koszewski_kowalczyk

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.hypekicks_koszewski_kowalczyk.R
import com.example.hypekicks_koszewski_kowalczyk.model.Sneaker

class SneakerAdapter(
    private val context: Context,
    private val list: List<Sneaker>
) : BaseAdapter() {

    override fun getCount(): Int = list.size
    override fun getItem(position: Int): Any = list[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_sneaker, parent, false)
        val sneaker = list[position]

        val brand = view.findViewById<TextView>(R.id.textBrand)
        val model = view.findViewById<TextView>(R.id.textModel)
        val image = view.findViewById<ImageView>(R.id.imageSneaker)

        brand.text = sneaker.brand
        model.text = sneaker.modelName

        Glide.with(context)
            .load(sneaker.imageUrl)
            .placeholder(android.R.drawable.progress_horizontal)
            .into(image)

        return view
    }
}
