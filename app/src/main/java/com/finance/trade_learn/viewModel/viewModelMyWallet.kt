package com.finance.trade_learn.viewModel

import android.util.Log
import com.finance.trade_learn.models.on_crypto_trade.BaseModelOneCryptoModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finance.trade_learn.ctryptoApi.cryptoService
import com.finance.trade_learn.database.dataBaseEntities.myCoins
import com.finance.trade_learn.database.dataBaseService
import com.finance.trade_learn.models.create_new_model_for_tem_history.NewModelForItemHistory
import kotlinx.coroutines.*
import java.math.BigDecimal

class viewModelMyWallet(val context: Context) : ViewModel() {
    val myCoinsDatabaseModel = MutableLiveData<List<myCoins>>()
    val myCoinsNewModel = MutableLiveData<ArrayList<NewModelForItemHistory>>()
    val myBaseModelOneCryptoModel = MutableLiveData<List<BaseModelOneCryptoModel>>()
    var disposable = CompositeDisposable()
    var totalValue = MutableLiveData<BigDecimal>()
    val databaseDao = dataBaseService.invoke(context).databaseDao()

    // this function fot get coins that i have
    fun getMyCoinsDetails(constrait: String? = null) {

        CoroutineScope(Dispatchers.Main).launch {
            if (constrait == null) {
                myCoinsDatabaseModel.value = databaseDao.getAllCoins()
                checkDatabaseData(myCoinsDatabaseModel)
            } else {
                myCoinsDatabaseModel.value = databaseDao.getConstraintCoin(constrait)
                checkDatabaseData(myCoinsDatabaseModel)
            }


        }


    }

    fun checkDatabaseData(myCoinsDatabaseModel: MutableLiveData<List<myCoins>>) {

        myCoinsDatabaseModel.let {
            var coinQuery = ""
            for (i in myCoinsDatabaseModel.value!!) {
                coinQuery += i.CoinName + ","
            }
            Log.i("iiiiiiiii", coinQuery)
            getDataFromApi(coinQuery.subSequence(0, coinQuery.length).toString())
        }
    }


    fun getDataFromApi(coinQuery: String) {

        disposable.add(
            cryptoService().getCoinIHave(coinQuery)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<BaseModelOneCryptoModel>>() {

                    override fun onSuccess(t: List<BaseModelOneCryptoModel>) {
                        myBaseModelOneCryptoModel.value = t
                        for (i in t) {
                            Log.i("iiiiiiiii", i.symbol)
                            Log.i("iiiiiiiii", i.price.toString())
                        }
                        createNewModel()


                    }

                    override fun onError(e: Throwable) {
                        Log.i("hatahataprice", e.message.toString())
                    }


                })
        )
    }


    fun createNewModel() {

        var total = BigDecimal.valueOf(0.0)
        val newModelForCoins = ArrayList<NewModelForItemHistory>()

        if (myCoinsDatabaseModel.value?.isNotEmpty() == true) {


            CoroutineScope(Dispatchers.IO).launch {
                var j = 0
                for (i in myBaseModelOneCryptoModel.value!!) {

                    for (z in myCoinsDatabaseModel.value!!) {
                        if (i.symbol == z.CoinName) {
                            Log.i("symbolcompare", i.symbol + " " + z.CoinName)
                            val name = i.symbol
                            val price = i.price.toBigDecimal()

                            val amount = databaseDao.getOneCoin(name).CoinAmount.toBigDecimal()


                            val image = i.logo_url

                            Log.i("symbolcompare", amount.toString())
                            j++

                            total += (price * amount)

                            newModelForCoins.add(
                                NewModelForItemHistory(
                                    name, amount.toString(),
                                    (amount * price).toString(), image
                                )
                            )

                        }


                    }

                    withContext(Dispatchers.Main) {

                        totalValue.value = total

                        myCoinsNewModel.value = newModelForCoins
                    }

                }
            }
        }


    }


    override fun onCleared() {
        disposable.clear()

        Log.i("clear", "clear")
        super.onCleared()
    }
}