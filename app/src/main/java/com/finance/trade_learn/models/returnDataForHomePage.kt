package com.finance.trade_learn.models

import androidx.lifecycle.MutableLiveData
import com.finance.trade_learn.enums.enumPriceChange
import com.finance.trade_learn.models.modelsConvector.CoinsHome

open class returnDataForHomePage (
    val ListOfCryptoforComparee: MutableLiveData<List<CoinsHome>> ,
    var ListOfCryptoo : MutableLiveData<ArrayList<CoinsHome>>,
    var changee :enumPriceChange

    )