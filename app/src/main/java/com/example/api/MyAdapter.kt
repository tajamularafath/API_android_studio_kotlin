package com.example.api

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso

class MyAdapter(val context: Activity, val productArrayList: List<Product>):RecyclerView.Adapter<MyAdapter.MyViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.eachitem, parent, false)
        Log.d("OnCreateViewHolder","Running Each Time ")
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = productArrayList[position]
        holder.title.text = currentItem.title

//        Using Picasso
        Picasso.get().load(currentItem.thumbnail).into(holder.image)
        Log.d("onBindViewHolder","Running onBindViewHolder ")

    }

    override fun getItemCount(): Int {
        Log.d("getItemCount","Running getItemCount ")
        return productArrayList.size
    }


    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var title: TextView
        var image: ShapeableImageView

        init {
            title = itemView.findViewById(R.id.productTitle)
            image = itemView.findViewById(R.id.productImage)
        }
    }
}