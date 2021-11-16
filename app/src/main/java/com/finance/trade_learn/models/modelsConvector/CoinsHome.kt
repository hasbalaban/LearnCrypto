package com.finance.trade_learn.models.modelsConvector

import android.graphics.Color
import android.graphics.drawable.Drawable
import com.finance.trade_learn.R
import com.finance.trade_learn.enums.enumPriceChange

data class CoinsHome(
    val CoinName: String,
    val CoinPrice: String,
    val CoinChangePercente: String,
    val CoinImage: String,
    val raise: enumPriceChange


)