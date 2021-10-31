package com.finance.trade_learn.ctryptoApi

import com.finance.trade_learn.models.BaseModelCrypto
import com.finance.trade_learn.models.on_crypto_trade.BaseModelOneCryptoModel
import com.finance.trade_learn.utils.newApiKey
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class cryptoService() {
    val ApiHolder = newApiKey()
    val Base_url = "https://api.nomics.com/v1/"
    var retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(Base_url)
        .build()
        .create(CryptoOperationInterface::class.java)

    fun PopulerCrypto(): Single<List<BaseModelCrypto>> {
        val newApi = ApiHolder.create()
        return retrofit.getPopulerCrypto(newApi)

    }

    fun AllCrypto(): Single<List<BaseModelCrypto>> {
        val newApi = ApiHolder.create()
        return retrofit.getAllCrypto(newApi)

    }

    fun AllCrypto1000(): Single<List<BaseModelCrypto>> {
        val newApi = ApiHolder.create()
        return retrofit.AllCrypto200(newApi)

    }

    fun selectedCoinToTrade(coinName: String): Single<List<BaseModelOneCryptoModel>> {
        val newApi = ApiHolder.create()
        return retrofit.selectedCoinToTrade(coinName=coinName,apiKey = newApi)
    }

    fun getCoinIHave(coinQuery: String): Single<List<BaseModelOneCryptoModel>> {
        val newApi = ApiHolder.create()
        return retrofit.getCoinIHave(coinQuery = coinQuery, apiKey = newApi)
    }


}