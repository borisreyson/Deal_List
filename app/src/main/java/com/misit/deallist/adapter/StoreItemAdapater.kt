package com.misit.deallist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.misit.deallist.R
import com.misit.deallist.responses.StoreItem
import com.squareup.picasso.Picasso

class StoreItemAdapater (
    private val context: Context?,
    private val storList: List<StoreItem>):
    RecyclerView.Adapter<StoreItemAdapater.myViewHolder>() {

    private val layoutInflater: LayoutInflater
    private var onItemClickListener: OnItemClickListener? = null

    class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView = itemView.findViewById<View>(R.id.image_view) as ImageView
        var nameTextView= itemView.findViewById<View>(R.id.tv_nama) as TextView
        var discountTextView = itemView.findViewById<View>(R.id.tv_discount) as TextView
        var container = itemView.findViewById<CardView>(R.id.container) as CardView
    }

    override fun onBindViewHolder(holder: StoreItemAdapater.myViewHolder, position: Int) {
        var store = storList[position]
        holder.nameTextView?.text = store.name
        holder.discountTextView?.text = "Belum Ada Deal"
        if(store.photo!=null){
            Picasso.get().load(store?.photo).into(holder.imageView)
        }
        if(onItemClickListener != null){
            holder.container?.setOnClickListener{onItemClickListener?.onItemClick(store.id)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val view = layoutInflater.inflate(R.layout.item_store,parent,false)
        return  myViewHolder(view)
    }

    override fun getItemCount(): Int {
        return storList.size
    }
    interface OnItemClickListener{
       fun onItemClick(storeId:String?)
    }
    fun setListener (listener:OnItemClickListener){
        onItemClickListener = listener
    }
    init {
        layoutInflater = LayoutInflater.from(context)
    }
}