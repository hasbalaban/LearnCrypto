package com.finance.trade_learn.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finance.trade_learn.database.dataBaseEntities.SaveCoin
import com.finance.trade_learn.database.dataBaseService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class viewModelHistoryTrade : ViewModel() {
    val listOfTrade = MutableLiveData<ArrayList<SaveCoin>>()

    fun getDataFromDatabase(context: Context) {
        val dao = dataBaseService.invoke(context).databaseDao()
        CoroutineScope(Dispatchers.Main).launch {
            val list = dao.getAllTrades()
            convertListForAdapter(list)
        }


    }

    private fun convertListForAdapter(list: List<SaveCoin>) {

        val newModel = ArrayList<SaveCoin>()
        for (i in list) {
            val name = i.coinName
            val amount = i.coinAmount.toBigDecimal()
            val price = i.coinPrice.toBigDecimal()
            val total = i.total.toBigDecimal()
            val date = i.date
            val state = i.tradeOperation
            val itemOfHistory = SaveCoin(
                i.tradeId, name, amount.toString(),
                price.toString(), total.toString(), date, state
            )
            newModel.add(itemOfHistory)

        }

        listOfTrade.value = newModel

    }
}