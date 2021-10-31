package com.finance.trade_learn.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.finance.trade_learn.database.dataBaseEntities.myCoins
import com.finance.trade_learn.database.dataBaseService
import com.finance.trade_learn.utils.sharedPreferencesManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.finance.trade_learn.database.dataBaseEntities.*

class viewModelUtils() : ViewModel() {



    fun isOneEntering(context: Context) {
        val sharedManager = sharedPreferencesManager(context)
        val isFirst = sharedManager.getSharedPreferencesBoolen("isfirst")
        Log.i( "isFirst", isFirst.toString())
        if (isFirst) {
            sharedManager.addSharedPreferencesBoolen("isfirst", false)
            addDollarsForOneTime(context)

        }


    }


    private fun addDollarsForOneTime(context: Context) {
        val addDollars = 1000.0
        addOneTimeDollars(addDollars, context)

    }

    private fun addOneTimeDollars(dollars: Double, context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val databaseDao=dataBaseService.invoke(context).databaseDao()
            val myCoins=myCoins("USDT",dollars)
            databaseDao.addCoin(myCoins)
        }
    }





}