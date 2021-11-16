package com.finance.trade_learn.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.finance.trade_learn.R
import com.finance.trade_learn.database.dataBaseEntities.myCoins
import com.finance.trade_learn.databinding.ItemForMyWalletCoinsBinding
import com.finance.trade_learn.models.create_new_model_for_tem_history.NewModelForItemHistory
import com.finance.trade_learn.utils.setImage
import com.finance.trade_learn.utils.setImageSvg

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
        val imageView=holder.view.coinImage
        val image_url = myCoinList[position].Image
        holder.view.coinImage.setImageSvg(image_url)
       // setCoinImage(imageView,image_url)

    }

    override fun getItemCount(): Int {
        return myCoinList.size
    }

    fun updateRecyclerView(list: ArrayList<NewModelForItemHistory>) {
        myCoinList.clear()
        myCoinList.addAll(list)
        notifyDataSetChanged()

    }

    fun setCoinImage(image:ImageView,image_url:String){

        image.load(image_url){
            placeholder(R.drawable.coin)
            crossfade(true)
            crossfade(1000)
            transformations(RoundedCornersTransformation(30f))
        }

    }
}