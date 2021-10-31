package com.finance.trade_learn.viewModel

import com.finance.trade_learn.models.BaseModelCrypto
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finance.trade_learn.ctryptoApi.cryptoService
import com.finance.trade_learn.enums.enumPriceChange
import com.finance.trade_learn.models.modelsConvector.Percent
import com.finance.trade_learn.models.modelsConvector.CoinsHome
import com.finance.trade_learn.utils.converOperation
import dagger.hilt.android.qualifiers.ApplicationContext
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
    private var disposable:CompositeDisposable= CompositeDisposable()
    var state = MutableLiveData<Boolean>()
    var ListOfCrypto = MutableLiveData<ArrayList<CoinsHome>>()
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
        change = data.changee
        ListOfCryptoforCompare = data.ListOfCryptoforComparee

    }

    override fun onCleared() {
        disposable.clear()

        Log.i("clear", "clear")
        super.onCleared()
    }


}