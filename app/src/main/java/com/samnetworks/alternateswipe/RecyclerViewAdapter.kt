package com.samnetworks.alternateswipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.viewpager_item,parent,false))
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ViewHolder){
            holder.bindData(position)
        }
    }
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var imageView: ImageView? = null;
        init{
            imageView = itemView.findViewById(R.id.image_view);
        }
        fun bindData(position:Int){
            itemView.tag = position
            when {
                position%3==0 -> {
                    imageView?.setImageResource(R.drawable.lake)
                }
                position%3==1 -> {
                    imageView?.setImageResource(R.drawable.sunset)
                }
                position%3==2 -> {
                    imageView?.setImageResource(R.drawable.garden)
                }
            }
        }
    }
}
