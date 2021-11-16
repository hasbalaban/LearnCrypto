package com.finance.trade_learn.utils

import androidx.lifecycle.MutableLiveData
import com.finance.trade_learn.enums.enumPriceChange
import com.finance.trade_learn.models.BaseModelCrypto
import com.finance.trade_learn.models.modelsConvector.CoinsHome
import com.finance.trade_learn.models.modelsConvector.Percent
import com.finance.trade_learn.models.returnDataForHomePage
import java.util.*
import kotlin.collections.ArrayList

class converOperation(val t: List<BaseModelCrypto>,
   val ListOfCryptoforCompare: MutableLiveData<List<CoinsHome>>
){

    var change = enumPriceChange.notr
    var ListOfCrypto = MutableLiveData<ArrayList<CoinsHome>>()
    fun convertDataToUse():returnDataForHomePage {


        val ListItem = arrayListOf<CoinsHome>()
        val ListItemForCompare = arrayListOf<CoinsHome>()
        var position = 0

        for (i in t) {
            val last = ListOfCryptoforCompare.value?.get(position)?.CoinPrice?.toDouble()
            val new = i.price.toDouble()

            //control for state of change
            if (last != null) {
                if (last > new) {
                    change = enumPriceChange.negative


                } else if (new > last) {
                    change = enumPriceChange.pozitive

                } else if (new.toString() == last.toString()) {
                    change = enumPriceChange.notr
                }
            }

            val coinImage=i.logo_url
            val coinName = i.symbol.uppercase(Locale.getDefault()) + " / USD"
            val coinPrice = (i.price.toString()+"00000000").subSequence(0, 8).toString()
            val percenteChange = percenteChange(i.day1.price_change_pct)


            //set lenght of percent as char
            val coinPercenteChange = percenteChange.raise + (percenteChange.percentChange
                .toString()+"0000").subSequence(0, 4).toString() + "%"

            val item = CoinsHome(coinName, coinPrice, coinPercenteChange,coinImage, change)

            val coinPercenteChangeCompare = percenteChange.percentChange * 100.0
            val coinPriceCompare = i.price.toString()
            val itemCompare =
                CoinsHome(
                    coinName,
                    coinPriceCompare,
                    coinPercenteChangeCompare.toString(),
                    coinImage,
                    enumPriceChange.notr
                )

            ListItemForCompare.add(itemCompare)
            ListItem.add(item)
            position++
        }

        ListOfCrypto.value = ListItem
        ListOfCryptoforCompare.value = ListItemForCompare

        return returnDataForHomePage(ListOfCryptoforCompare, ListOfCrypto, change)


    }

    fun percenteChange(coinPrice: String): Percent {
        var pct: Percent? = null
        when (coinPrice.subSequence(0, 1)) {

            "-" -> pct =
                Percent(
                    coinPrice.subSequence(1, coinPrice.length).toString()
                        .toDouble() * 100, "-"
                )
            else -> pct =
                Percent(
                    coinPrice.subSequence(1, coinPrice.length).toString()
                        .toDouble() * 100, "+"
                )


        }
        return pct

    }


}