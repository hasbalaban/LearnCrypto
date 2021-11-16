package com.finance.trade_learn.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.finance.trade_learn.R


fun ImageView.setImage( context: Context, URL:String){
        Glide.with(context) .load(URL).into(this)

}




fun ImageView.setImageSvg(URL:String){

        this.load(URL){
          //      placeholder(R.drawable.placeholder)
                crossfade(true)
                crossfade(200)
                transformations(CircleCropTransformation())
              //  transformations(RoundedCornersTransformation(30f))
        }


}




