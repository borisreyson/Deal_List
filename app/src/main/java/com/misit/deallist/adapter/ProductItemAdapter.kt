package com.misit.deallist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.misit.deallist.R
import com.misit.deallist.responses.Product
import com.squareup.picasso.Picasso

class ProductItemAdapter (
    private val context: Context?,
    private val productList: MutableList<Product>):
    RecyclerView.Adapter<ProductItemAdapter.myViewHolder>() {

    private val layoutInflater: LayoutInflater
    private var onItemClickListener: OnItemClickListener? = null

    class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgProduct = itemView.findViewById<View>(R.id.imgProduct) as ImageView
        var idNamaProduct= itemView.findViewById<View>(R.id.idNamaProduct) as TextView
        var idHargaProduct = itemView.findViewById<View>(R.id.idHargaProduct) as TextView
        //var productItem = itemView.findViewById<CardView>(R.id.productItem) as LinearLayout

    }

    override fun onBindViewHolder(holder: ProductItemAdapter.myViewHolder, position: Int) {
        var product = productList[position]
        //holder.tes?.text = store.name
        //var store = storList[position]
        holder.idNamaProduct?.text = product.name
        holder.idHargaProduct?.text = product.price.toString()
        if(product.photo!=null){
            Picasso.get().load(product?.photo).into(holder.imgProduct)
        }
        if(onItemClickListener != null){
            holder.imgProduct?.setOnClickListener{onItemClickListener?.onItemClick(product.id.toString())}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val view = layoutInflater.inflate(R.layout.item_product,parent,false)
        return  myViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productList.size
    }
    interface OnItemClickListener{
        fun onItemClick(productId:String?)
    }
    fun setListener (listener:OnItemClickListener){
        onItemClickListener = listener
    }
    init {
        layoutInflater = LayoutInflater.from(context)
    }
}