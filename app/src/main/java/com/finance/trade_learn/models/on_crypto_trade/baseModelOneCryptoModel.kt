package com.finance.trade_learn.models.on_crypto_trade

import com.google.gson.annotations.SerializedName

data class BaseModelOneCryptoModel (

	val id : String,
	val currency : String,
	val symbol : String,
	val name : String,
	val logo_url : String,
	val status : String,
	val price : Double,
	val price_date : String,
	val price_timestamp : String,
	val circulating_supply : Long,
	val max_supply : Long,
	val market_cap : Long,
	val market_cap_dominance : Double,
	val num_exchanges : Long,
	val num_pairs : Long,
	val num_pairs_unmapped : Long,
	val first_candle : String,
	val first_trade : String,
	val first_order_book : String,
	val rank : Int,
	val rank_delta : Int,
	val high : Double,
	val high_timestamp : String,
	@SerializedName("1d")
	val d1 : d1,
	@SerializedName("7d")
	val d7 : d7,
	@SerializedName("30d")
	val d30 : d30,
	@SerializedName("365d")
	val d365 : d365,
	val ytd : Ytd
)