package com.finance.trade_learn.Adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.finance.trade_learn.R
import com.finance.trade_learn.clickListener.HomePageClickListener
import com.finance.trade_learn.databinding.ItemCoinOfTodayBinding
import com.finance.trade_learn.enums.enumPriceChange
import com.finance.trade_learn.models.modelsConvector.CoinsHome
import com.finance.trade_learn.utils.DifferentItems
import com.finance.trade_learn.utils.setImageSvg
import com.finance.trade_learn.utils.sharedPreferencesManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class adapter_for_hot_coins(val context: Context, val list: ArrayList<CoinsHome>) :
    RecyclerView.Adapter<adapter_for_hot_coins.viewHolder>() {

    private var firstEnter = true
    private var border = 0

    class viewHolder(val view: ItemCoinOfTodayBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val inflater = LayoutInflater.from(context)
        val view = DataBindingUtil.inflate<ItemCoinOfTodayBinding>(
            inflater, R.layout.item_coin_of_today, parent, false
        )
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val a = position
        border = a

        when (list[position].raise) {

            enumPriceChange.pozitive -> {
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

        holder.view.coin = list[position]

        holder.view.LayoutCoin.setOnClickListener {
            val coinName = SolveCoinName(list[position].CoinName)

            sharedPreferencesManager(context)
                .addSharedPreferencesString("coinName", coinName)
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


    fun updateData(newList: ArrayList<CoinsHome>) {
        if (firstEnter) {
            list.addAll(newList)
            notifyDataSetChanged()
            // this code will change everything.
            firstEnter = !firstEnter
        } else {
            updateLastList(newList, list)
            // list.clear()
            // list.addAll(newList)
        }


    }

    private fun updateLastList(newList: ArrayList<CoinsHome>, oldList: ArrayList<CoinsHome>) {

        CoroutineScope(Dispatchers.IO).launch {
            val diff = DifferentItems<CoinsHome>(oldList, newList).comporeItems()
            if (diff.isNotEmpty()) {
                for (i in 0..diff.size - 1) {
                    val newItem = diff[i].comparedList
                    list[diff[i].position] = newItem
                    withContext(Dispatchers.Main) {

                        if (diff[i].position <= border && diff[i].position + 5> border) {
                            notifyItemChanged(diff[i].position)
                        }
                    }

                }
            }
        }


    }


}