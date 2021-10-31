package com.finance.trade_learn.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.finance.trade_learn.viewModel.viewModelMyWallet
import com.finance.trade_learn.Adapters.adapter_for_my_wallet
import com.finance.trade_learn.R
import com.finance.trade_learn.databinding.FragmentWalletPageBinding
import com.finance.trade_learn.models.create_new_model_for_tem_history.NewModelForItemHistory
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import kotlin.collections.ArrayList

class walletPage : Fragment(), TextWatcher {


    var viewVisible = true
    lateinit var dataBindingWallet: FragmentWalletPageBinding
    lateinit var adapter: adapter_for_my_wallet
    lateinit var viewModelMyWallet: viewModelMyWallet
    var disposable = CompositeDisposable()

    var myCoinsList = ArrayList<NewModelForItemHistory>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = adapter_for_my_wallet(arrayListOf())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBindingWallet = DataBindingUtil.inflate(
            inflater, R.layout.fragment_wallet_page,
            container, false
        )

        //      demo()
        return dataBindingWallet.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setup()
        observerFun()

        super.onViewCreated(view, savedInstanceState)
    }


    fun setup() {
        viewModelMyWallet = viewModelMyWallet(requireContext())
        viewModelMyWallet.getMyCoinsDetails()


        dataBindingWallet.serachMyCoins.addTextChangedListener(this)

        dataBindingWallet.myCoins.layoutManager = LinearLayoutManager(requireContext())
        dataBindingWallet.myCoins.adapter = adapter

    }

    fun observerFun() {
        getMyWalletDetails()


    }


    fun getMyWalletDetails() {

        if (viewVisible) {
            viewModelMyWallet.myCoinsNewModel.observe(viewLifecycleOwner, Observer {
                myCoinsList.clear()
                myCoinsList.addAll(it)


                adapter.updateRecyclerView(it)

                if (viewVisible) {


                    viewModelMyWallet.totalValue.observe(
                        viewLifecycleOwner,
                        Observer { totalValue ->
                            val text = "≈ "
                            dataBindingWallet.totalValue.setText(
                                (text + (totalValue.toString() + "000000000000")).subSequence(
                                    0,
                                    10
                                ).toString()
                            )


                        })


                }
            })

        }
    }

    override fun onResume() {
        getMyWalletDetails()
        super.onResume()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable?) {

        val queryCoin = dataBindingWallet.serachMyCoins.text.toString()
            .uppercase(Locale.getDefault())
        if (queryCoin != "") {

            val newList = myCoinsList.filter { item ->
                item.CoinName.contains(queryCoin)
            }

            adapter.updateRecyclerView(newList as ArrayList<NewModelForItemHistory>)


        } else {
            adapter.updateRecyclerView(myCoinsList as ArrayList<NewModelForItemHistory>)


        }

    }


}