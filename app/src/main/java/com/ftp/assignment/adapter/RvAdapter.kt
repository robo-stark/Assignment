package com.ftp.assignment.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ftp.assignment.databinding.ItemViewRvBinding
import com.ftp.assignment.model.DataModel

var myList =  ArrayList<DataModel>()
class RvAdapter : RecyclerView.Adapter<RvAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemViewRvBinding) : RecyclerView.ViewHolder(binding.root)

    var onPostClick : ((DataModel) -> Unit) ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemViewRvBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = myList[position]
        holder.binding.apply {
            id.text = data.id.toString()
            title.text = data.title
        }
        holder.itemView.setOnClickListener { onPostClick?.invoke(myList[position]) }
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(list : ArrayList<DataModel>) {
        myList = list
        notifyDataSetChanged()
    }

}