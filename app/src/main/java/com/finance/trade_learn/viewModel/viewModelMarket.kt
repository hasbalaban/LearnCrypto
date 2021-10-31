package com.finance.trade_learn.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finance.trade_learn.ctryptoApi.cryptoService
import com.finance.trade_learn.enums.enumPriceChange
import com.finance.trade_learn.models.BaseModelCrypto
import com.finance.trade_learn.models.modelsConvector.CoinsHome
import com.finance.trade_learn.utils.converOperation
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class viewModelMarket:ViewModel() {
    private var disposable: CompositeDisposable = CompositeDisposable()
    var state = MutableLiveData<Boolean>()
    var ListOfCrypto = MutableLiveData<ArrayList<CoinsHome>>()
    private var ListOfCryptoforCompare = MutableLiveData<List<CoinsHome>>()
    lateinit var job: Job
    var change = enumPriceChange.notr


    fun runGetAllCryptoFromApi() {
        state.value = false
        CoroutineScope(Dispatchers.IO).launch {
            disposable.add(
                cryptoService().AllCrypto1000()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<BaseModelCrypto>>() {
                        override fun onSuccess(t: List<BaseModelCrypto>) {
                            //convert data fun
                            convert(t)
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