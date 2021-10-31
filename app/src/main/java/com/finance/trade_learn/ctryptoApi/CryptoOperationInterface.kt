package com.finance.trade_learn.ctryptoApi

import com.finance.trade_learn.models.BaseModelCrypto
import com.finance.trade_learn.models.on_crypto_trade.BaseModelOneCryptoModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoOperationInterface {


    @GET("currencies/ticker?ids=BTC,ETH,BNB&convert=USD")
    fun getPopulerCrypto(
        @Query("key") apiKey: String
    ): Single<List<BaseModelCrypto>>

    @GET("currencies/ticker?convert=USD&per-page=50&page=1")
    fun getAllCrypto(
        @Query("key") apiKey: String
    ): Single<List<BaseModelCrypto>>

    @GET("currencies/ticker?convert=USD&per-page=1000&page=1")
    fun AllCrypto200(
        @Query("key") apiKey: String
    ): Single<List<BaseModelCrypto>>

    @GET("currencies/ticker?convert=USD")
    fun selectedCoinToTrade(
        @Query("key") apiKey: String, @Query("ids") coinName: String
    ): Single<List<BaseModelOneCryptoModel>>

    @GET("currencies/ticker?convert=USD")
    fun getCoinIHave(
        @Query("key") apiKey: String,
        @Query("ids") coinQuery: String
    ): Single<List<BaseModelOneCryptoModel>>


}