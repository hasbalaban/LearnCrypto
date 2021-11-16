package com.finance.trade_learn.Adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.finance.trade_learn.Adapters.adapter_for_hot_coins
import androidx.recyclerview.widget.RecyclerView
import com.finance.trade_learn.R
import com.finance.trade_learn.clickListener.HomePageClickListener
import com.finance.trade_learn.clickListener.MarketClickListener
import com.finance.trade_learn.databinding.ItemCoinOfTodayBinding
import com.finance.trade_learn.enums.enumPriceChange
import com.finance.trade_learn.models.modelsConvector.CoinsHome
import com.finance.trade_learn.utils.setImageSvg
import com.finance.trade_learn.utils.sharedPreferencesManager
import org.xmlpull.v1.XmlPullParser

class adapter_for_hot_coins(val context: Context, val list: ArrayList<CoinsHome>) :
    RecyclerView.Adapter<adapter_for_hot_coins.viewHolder>() {
    class viewHolder(val view: ItemCoinOfTodayBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val inflater = LayoutInflater.from(context)
        val view = DataBindingUtil.inflate<ItemCoinOfTodayBinding>(
            inflater, R.layout.item_coin_of_today, parent, false
        )
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {

        when (list[position].raise) {

            enumPriceChange.pozitive ->{
                holder.view.coinPrice.setTextColor(Color.parseColor("#2ebd85"))
            }
            enumPriceChange.negative -> {
                holder.view.coinPrice.setTextColor(Color.parseColor("#F6465D"))
            }
            enumPriceChange.notr -> {
               holder.view.coinPrice.setTextColor(Color.parseColor("#000000"))
            }
        }


        when (list[position].CoinChangePercente.subSequence(0, 1)) {

            "-" -> {
                holder.view.coinPercent.setTextColor(Color.parseColor("#ffffff"))
                holder.view.coinPercent.setBackgroundColor(Color.parseColor("#F6465D"))
            }
            "+" -> {
                holder.view.coinPercent.setTextColor(Color.parseColor("#ffffff"))
                holder.view.coinPercent.setBackgroundColor(Color.parseColor("#2ebd85"))
            }
            else -> {
                holder.view.coinPrice.setBackgroundColor(Color.parseColor("#ffffff"))
                holder.view.coinPercent.setTextColor(Color.parseColor("#000000"))
            }


        }

        holder.view.coin=list[position]

        holder.view.LayoutCoin.setOnClickListener {
            val coinName= SolveCoinName( list[position].CoinName)

           sharedPreferencesManager(context)
                .addSharedPreferencesString("coinName",coinName)
            HomePageClickListener().ClickListener(it)
        }
        holder.view.coinImage.setImageSvg(list[position].CoinImage)
    }

    override fun getItemCount(): Int {
        return list.size
    }



    fun SolveCoinName(coinName: String): String {
        var resolvedName = ""
        for (i in coinName) {
            if (i.toString() == " ") {
                break
            } else {

                resolvedName += i
            }

        }
        return resolvedName

    }


    fun updateData (newList:ArrayList<CoinsHome>){
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()

    }



}