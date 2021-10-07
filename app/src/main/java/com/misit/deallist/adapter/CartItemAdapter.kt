package com.misit.deallist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.misit.deallist.R
import com.misit.deallist.model.Cart
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_cart.view.*
import org.w3c.dom.Text

class CartItemAdapter(
    private val context: Context?,
    private var cartList: List<Cart?>):
    RecyclerView.Adapter<CartItemAdapter.MyViewHolder>() {
    private var layoutInflater : LayoutInflater
    private var listener : OnClickListener? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartItemAdapter.MyViewHolder {
        val view = layoutInflater.inflate(R.layout.item_cart,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(holder: CartItemAdapter.MyViewHolder, position: Int) {
        val cart = cartList[position]
        holder.name.text = cart?.productName
        holder.price.text = cart?.price.toString()
        if(cart?.photo!=null){
            Picasso.get().load(cart?.photo).into(holder.photo)
        }

    }
 class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
     val name:TextView = itemView.findViewById<View>(R.id.tv_name) as TextView
     val price:TextView = itemView.findViewById<View>(R.id.tv_price) as TextView
     val photo:ImageView = itemView.findViewById<View>(R.id.ImgProducts) as ImageView
 }
    interface OnClickListener{
        fun onClickItem(productId:String)
    }
    fun setListener(mListener:OnClickListener){
        listener = mListener
    }
    init {
        layoutInflater = LayoutInflater.from(context)
    }
}