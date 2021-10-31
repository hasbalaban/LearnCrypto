package com.finance.trade_learn.Adapters

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.finance.trade_learn.R
import com.finance.trade_learn.database.dataBaseEntities.SaveCoin
import com.finance.trade_learn.databinding.ItemOfHistoryBinding
import com.finance.trade_learn.enums.tradeEnum

class adapter_for_history_trade(val list_of_trade: ArrayList<SaveCoin>) :
    RecyclerView.Adapter<adapter_for_history_trade.viewHolder>() {
    inner class viewHolder(val view: ItemOfHistoryBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemOfHistoryBinding>(
            inflater,
            R.layout.item_of_history,
            parent,
            false
        )

        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.view.trade = list_of_trade[position]

        if (list_of_trade[position].tradeOperation==tradeEnum.Sell.toString()){
            setColor(holder.view)
        }

    }

    override fun getItemCount(): Int {

        return list_of_trade.size
    }

    fun setColor(view: ItemOfHistoryBinding) {
        view.operation.setTextColor(Color.parseColor("#F6465D"))
        view.total.setTextColor(Color.parseColor("#2ebd85"))

        /*
        view.coinAmount.setTextColor(Color.parseColor("#F6465D"))
        view.coinName.setTextColor(Color.parseColor("#F6465D"))
        view.coinPrice.setTextColor(Color.parseColor("#F6465D"))
        view.time.setTextColor(Color.parseColor("#F6465D"))

         */
    }

}