package com.finance.trade_learn.Adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.finance.trade_learn.R
import com.finance.trade_learn.databinding.ItemOfPopulerCoinsBinding
import com.finance.trade_learn.enums.enumPriceChange
import com.finance.trade_learn.models.modelsConvector.CoinsHome

class adapter_for_populer_coins(val context: Context, val list: ArrayList<CoinsHome>) :
    RecyclerView.Adapter<adapter_for_populer_coins.ViewHolder>() {

    class ViewHolder(val view: ItemOfPopulerCoinsBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(context)
        val view = DataBindingUtil.inflate<ItemOfPopulerCoinsBinding>(
            inflater,
            R.layout.item_of_populer_coins,
            parent,
            false
        )

        return ViewHolder(view)

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (list[position].raise) {

            enumPriceChange.pozitive -> {
                holder.view.populerCoinPrice.setTextColor(Color.parseColor("#2ebd85"))
            }
            enumPriceChange.negative -> {
                holder.view.populerCoinPrice.setTextColor(Color.parseColor("#F6465D"))
            }
            enumPriceChange.notr -> {
                holder.view.populerCoinPrice.setTextColor(Color.parseColor("#000000"))
            }
        }
        when (list[position].CoinChangePercente.subSequence(0, 1)) {

            "-" -> holder.view.populerCoinChangePercent.setTextColor(Color.parseColor("#F6465D"))
            "+" -> holder.view.populerCoinChangePercent.setTextColor(Color.parseColor("#2ebd85"))
            else -> holder.view.populerCoinChangePercent.setTextColor(Color.parseColor("#000000"))


        }

        holder.view.populerCoin = list[position]

        //animations
        val animation = AnimationUtils.loadAnimation(
            context, R.anim.animation_for_item_of_recyclers
        )
        holder.view.layoutPopulerCoins.animation = animation


    }

    override fun getItemCount(): Int {
        return list.size
    }


    fun updatePopuler(newList: List<CoinsHome>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()

    }

}