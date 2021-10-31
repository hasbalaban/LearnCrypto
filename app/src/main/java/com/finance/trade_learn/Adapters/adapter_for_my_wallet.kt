package com.finance.trade_learn.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.finance.trade_learn.R
import com.finance.trade_learn.database.dataBaseEntities.myCoins
import com.finance.trade_learn.databinding.ItemForMyWalletCoinsBinding
import com.finance.trade_learn.models.create_new_model_for_tem_history.NewModelForItemHistory
import com.finance.trade_learn.utils.setImage

class adapter_for_my_wallet(var myCoinList: ArrayList<NewModelForItemHistory>) :
    RecyclerView.Adapter<adapter_for_my_wallet.viewHolder>() {

    class viewHolder(val view: ItemForMyWalletCoinsBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemForMyWalletCoinsBinding>(
            inflater, R.layout.item_for_my_wallet_coins, parent, false
        )
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.view.data = myCoinList[position]

        val animation = AnimationUtils.loadAnimation(
            holder.view.coinAmount.context, R.anim.animation_for_item_of_recyclers
        )
        holder.view.RelayoutWallet.animation = animation

    }

    override fun getItemCount(): Int {
        return myCoinList.size
    }

    fun updateRecyclerView(list: ArrayList<NewModelForItemHistory>) {
        myCoinList.clear()
        myCoinList.addAll(list)
        notifyDataSetChanged()

    }
}