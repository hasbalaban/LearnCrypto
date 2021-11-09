package com.finance.trade_learn.models

import com.google.gson.annotations.SerializedName

data class BaseModelCrypto(

    val id: String,
    val currency: String,
    val symbol: String,
    val name: String,
    val logo_url: String,
    val status: String,
    val price: String,
    val price_date: String,
    val price_timestamp: String,
    val circulating_supply: String,
    val max_supply: String,
    val market_cap: String,
    val market_cap_dominance: String,
    val num_exchanges: String,
    val num_pairs: String,
    val num_pairs_unmapped: String,
    val first_candle: String,
    val first_trade: String,
    val first_order_book: String,
    val rank: String,
    val rank_delta: String,
    val high: String,
    val high_timestamp: String,
    @SerializedName("1d")
    val day1: Day1,
    @SerializedName("7d")
    val Day7: Day7,
    @SerializedName("30d")
    val Day30: Day30,
    @SerializedName("365d")
    val Day365: Day365,
    val ytd: Ytd
)