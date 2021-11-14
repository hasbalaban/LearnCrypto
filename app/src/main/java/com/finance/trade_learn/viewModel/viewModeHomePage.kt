package com.finance.trade_learn.viewModel

import com.finance.trade_learn.models.BaseModelCrypto
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finance.trade_learn.ctryptoApi.cryptoService
import com.finance.trade_learn.enums.enumPriceChange
import com.finance.trade_learn.models.modelsConvector.CoinsHome
import com.finance.trade_learn.models.returnDataForHomePage
import com.finance.trade_learn.utils.converOperation
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class viewModeHomePage : ViewModel() {
    var isInitialize = false
    private var disposable: CompositeDisposable = CompositeDisposable()
    var state = MutableLiveData<Boolean>()
    var ListOfCrypto = MutableLiveData<ArrayList<CoinsHome>>()
    var ListOfCryptoForPopuler = MutableLiveData<ArrayList<CoinsHome>>()
    private var ListOfCryptoforCompare = MutableLiveData<List<CoinsHome>>()
    lateinit var job: Job
    var change = enumPriceChange.notr

    /*
        fun runGetPopulerCryptoFromApi() {


            state.value = false

           CoroutineScope(Dispatchers.IO).launch {
                disposable.add(
                    cryptoService().PopulerCrypto()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<BaseModelCrypto>>() {
                            override fun onSuccess(t: List<BaseModelCrypto>) {
                                Log.i("messages", "e.message!!")
                                //convert data fun
                                state.value = true
                            }

                            override fun onError(e: Throwable) {
                                Log.i("messages", e.message!!)
                                state.value = false
                            }
                        })
                )
            }




        }


     */
    fun runGetAllCryptoFromApi() {
        state.value = false
        CoroutineScope(Dispatchers.IO).launch {
            disposable.add(
                cryptoService().AllCrypto()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<BaseModelCrypto>>() {
                        override fun onSuccess(t: List<BaseModelCrypto>) {
                            //convert data fun
                            convert(t)
                            state.value = true
                            isInitialize = true

                        }

                        override fun onError(e: Throwable) {
                            state.value = false
                            Log.i("messages", e.message!!)


                        }
                    })
            )
        }


    }

    fun convert(t: List<BaseModelCrypto>) {
        val data = converOperation(t, ListOfCryptoforCompare).convertDataToUse()

        ListOfCrypto = data.ListOfCryptoo
        convertPopulerCoinList(data.ListOfCryptoo.value)
        change = data.changee
        ListOfCryptoforCompare = data.ListOfCryptoforComparee

    }

    private fun convertPopulerCoinList(list: ArrayList<CoinsHome>?) {

        var popList = arrayListOf<CoinsHome>()
        if (list != null) {
            for (i in list) {
                if (i.CoinName.subSequence(
                        0,
                        3
                    ) == "BTC" || i.CoinName.subSequence(
                        0, 3
                    ) == "BNB" || i.CoinName.subSequence(0, 3) == "ETH"
                ) {
                    popList.add(i)
                }
            }
            ListOfCryptoForPopuler.value = popList

        }

    }

    override fun onCleared() {
        disposable.clear()

        Log.i("clear", "clear")
        super.onCleared()
    }


}