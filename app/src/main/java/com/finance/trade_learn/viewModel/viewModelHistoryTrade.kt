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
    val listOfTrade = MutableLiveData<List<SaveCoin>>()

    fun getDataFromDatabase(context: Context) {
        val dao = dataBaseService.invoke(context).databaseDao()
        CoroutineScope(Dispatchers.Main).launch {
            listOfTrade.value = dao.getAllTrades()
        }


    }
}