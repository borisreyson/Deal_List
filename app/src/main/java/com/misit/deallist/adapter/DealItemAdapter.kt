package com.misit.deallist.adapter

import android.content.Context
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.misit.deallist.R
import com.misit.deallist.responses.DealItem
import com.squareup.picasso.Picasso
import org.joda.time.DateTime
import org.joda.time.Days
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DealItemAdapter(
    private val context: Context?,
    private val dealList: MutableList<DealItem>):
    RecyclerView.Adapter<DealItemAdapter.MyViewHolder>() {

    private val layoutInflater: LayoutInflater
    private var simpleDateFormat: SimpleDateFormat? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DealItemAdapter.MyViewHolder {

        val view = layoutInflater.inflate(R.layout.item_deal,parent,false)
        return DealItemAdapter.MyViewHolder(view)
    }

    override fun getItemCount(): Int {

        return dealList.size
    }

    override fun onBindViewHolder(holder: DealItemAdapter.MyViewHolder, position: Int) {
        var dealList = dealList[position]
        holder.idNamaProduct?.text = dealList.name
        holder.idHargaLama?.text = dealList.price.toString()
        holder.idHargaLama?.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        val diskon = dealList.discount!!.toInt()
        val priceOld = dealList.price!!.toInt()

        val newPrice = priceOld - (priceOld * diskon /100).toDouble()

        holder.idHargaBaru.text = newPrice.toString()

        val today = Date()

        try {
            val startDate = simpleDateFormat?.parse(dealList.startDate)
            val endDate = simpleDateFormat?.parse(dealList.endDate)

            val delta1 = today.time-startDate!!.time
            val delta2 = today.time-endDate!!.time


            val delta1InDays = Days.daysBetween(DateTime(today.time),DateTime(startDate.time)).days
            val delta2InDays = Days.daysBetween(DateTime(today.time),DateTime(endDate.time)).days

            Log.d("todayTime",delta1InDays.toString())
            if(delta1<0){
                holder.idDateDeal.text= "Diskon Mulai "+ Math.abs(delta1InDays) + "Hari Lagi"
            }else if(delta1>=0 && delta2 <=0 ){
                if(delta1InDays==0){
                    holder.idDateDeal.text= "Diskon Mulai Hari Ini"
                }else if(delta2InDays == 0){
                    holder.idDateDeal.text= "Diskon Berakhir Hari Ini"
                }else{
                    holder.idDateDeal.text= "Diskon Berakhir "+ Math.abs(delta2InDays+1) + "Hari Lagi"
                }
                holder.idDateDeal.text= "Diskon Berakhir "+ Math.abs(delta2InDays+1) + "Hari Lagi"
            }else{
                holder.idDateDeal.text= "Diskon Berakhir"

                holder.idHargaLama?.paintFlags = 0
                holder.idHargaBaru.text = null
            }
        }catch (e: ParseException){
                e.printStackTrace()
        }

        if(dealList.photo!=null){
            Picasso.get().load(dealList?.photo).into(holder.imgProduct)
        }
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgProduct = itemView.findViewById<View>(R.id.imgProduct) as ImageView
        var idNamaProduct= itemView.findViewById<View>(R.id.idNamaProduct) as TextView
        var idHargaLama = itemView.findViewById<View>(R.id.idHargaLama) as TextView
        var idHargaBaru = itemView.findViewById<View>(R.id.idHargaBaru) as TextView
        var idDateDeal = itemView.findViewById<View>(R.id.idDateDeal) as TextView


    }

    init {
        layoutInflater = LayoutInflater.from(context)
        simpleDateFormat= SimpleDateFormat("yyyy-MM-dd",Locale.US)
    }
}