package com.finance.trade_learn.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy

import android.net.Uri
import android.R

import android.graphics.drawable.PictureDrawable








fun ImageView.setImage( context: Context, URL:String){
        Glide.with(context) .load(URL).into(this)

}


