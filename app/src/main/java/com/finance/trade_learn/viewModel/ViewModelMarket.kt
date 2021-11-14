package com.finance.trade_learn.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.finance.trade_learn.ctryptoApi.cryptoService
import com.finance.trade_learn.enums.enumPriceChange
import com.finance.trade_learn.models.BaseModelCrypto
import com.finance.trade_learn.models.modelsConvector.CoinsHome
import com.finance.trade_learn.utils.converOperation
import dagger.hilt.EntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@ViewModelScoped
class ViewModelMarket @Inject constructor(@ApplicationContext application: Application) :
    AndroidViewModel(application) {

    var isInitiaize = false
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
                            isInitiaize =true
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
/*

class marketViewModelProvider(): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
     if (modelClass.isAssignableFrom(ViewModelMarket::class.java)){
         return ViewModelMarket() as T
     }
        throw IllegalArgumentException("dsfsds")
    }

}

 */